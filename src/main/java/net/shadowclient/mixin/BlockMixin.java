package net.shadowclient.mixin;

import net.minecraft.block.Block;
import net.shadowclient.main.module.ModuleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Inject(at = @At("HEAD"), method = "getVelocityMultiplier", cancellable = true)
    private void onGetVelocityMultiplier(CallbackInfoReturnable<Float> cir) {
        if (!ModuleManager.NoSlowdownModule.enabled) {
            return;
        }

        if (cir.getReturnValueF() < 1) {
            cir.setReturnValue(1f);
        }
    }
}
