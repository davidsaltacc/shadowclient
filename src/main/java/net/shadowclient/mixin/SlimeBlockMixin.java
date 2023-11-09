package net.shadowclient.mixin;

import net.minecraft.block.SlimeBlock;
import net.minecraft.util.math.Vec3d;
import net.shadowclient.main.module.ModuleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SlimeBlock.class)
public abstract class SlimeBlockMixin {
    @Redirect(method = "onSteppedOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;multiply(DDD)Lnet/minecraft/util/math/Vec3d;"))
    private Vec3d onMultiplyVelocity(Vec3d velocity, double x, double y, double z) {
        if (ModuleManager.NoSlowdownModule.enabled) {
            return velocity;
        }
        return velocity.multiply(x, y, z);
    }
}
