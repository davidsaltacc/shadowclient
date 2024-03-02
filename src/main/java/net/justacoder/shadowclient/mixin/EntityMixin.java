package net.justacoder.shadowclient.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.event.events.VelocityFromEntityEvent;
import net.justacoder.shadowclient.main.event.events.VelocityFromFluidEvent;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.util.ColorUtils;
import net.justacoder.shadowclient.mixininterface.IEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntity {

    @Final
    @Shadow
    private static AtomicInteger CURRENT_ID;

    @Inject(method = "getTeamColorValue", at = @At("HEAD"), cancellable = true)
    private void injected(CallbackInfoReturnable<Integer> cir) {
        if (ModuleManager.EntitiesESPModule.enabled) {
            int[] colors = ModuleManager.EntitiesESPModule.getColor((Entity) (Object) this);

            int color = ColorUtils.RGB2int(colors[0], colors[1], colors[2]);

            cir.setReturnValue(color);
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V", opcode = Opcodes.INVOKEVIRTUAL, ordinal = 0), method = "updateMovementInFluid(Lnet/minecraft/registry/tag/TagKey;D)Z")
    private void setVelocityFromFluid(Entity entity, Vec3d velocity) {
        VelocityFromFluidEvent event = new VelocityFromFluidEvent((Entity) (Object) this);
        EventManager.fireEvent(event);

        if (!event.cancelled) {
            entity.setVelocity(velocity);
        }
    }

    @Inject(at = @At("HEAD"), method = "pushAwayFrom(Lnet/minecraft/entity/Entity;)V", cancellable = true)
    private void onPushAwayFrom(Entity entity, CallbackInfo ci) {

        VelocityFromEntityEvent evt = new VelocityFromEntityEvent((Entity) (Object) this);

        EventManager.fireEvent(evt);

        if (evt.cancelled) {
            ci.cancel();
        }
    }


    @Inject(at = @At("RETURN"), method = "isInvisibleTo(Lnet/minecraft/entity/player/PlayerEntity;)Z", cancellable = true)
    private void onIsInvisibleTo(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ()) {
            return;
        }

        if (ModuleManager.SeeInvisiblesModule.visible((Entity) (Object) this)) {
            cir.setReturnValue(false);
        }
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    public AtomicInteger getCurrentId() {
        return CURRENT_ID;
    }
}
