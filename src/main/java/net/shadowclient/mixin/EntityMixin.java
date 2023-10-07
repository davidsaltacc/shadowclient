package net.shadowclient.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.shadowclient.main.SCMain;
import net.shadowclient.main.event.events.VelocityFromFluidEvent;
import net.shadowclient.main.module.ModuleManager;
import net.shadowclient.main.module.modules.render.EntitiesESP;
import net.shadowclient.main.util.ColorUtils;
import net.shadowclient.mixininterface.IEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntity {

    @Shadow
    private boolean onGround;

    @Inject(method = "getTeamColorValue", at = @At("HEAD"), cancellable = true)
    private void injected(CallbackInfoReturnable<Integer> cir) {
        if (ModuleManager.getModule("entitiesesp").enabled) {
            int[] colors = ((EntitiesESP) ModuleManager.getModule("entitiesesp")).getColor((Entity) (Object) this);

            int color = ColorUtils.RGB2int(colors[0], colors[1], colors[2]);

            cir.setReturnValue(color);
        }
    }

    @Override
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V", opcode = Opcodes.INVOKEVIRTUAL, ordinal = 0), method = "updateMovementInFluid(Lnet/minecraft/registry/tag/TagKey;D)Z")
    private void setVelocityFromFluid(Entity entity, Vec3d velocity)
    {
        VelocityFromFluidEvent event = new VelocityFromFluidEvent((Entity) (Object) this);
        SCMain.OnEvent(event);

        if (!event.cancelled) {
            entity.setVelocity(velocity);
        }
    }
}
