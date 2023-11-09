package net.shadowclient.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.shadowclient.main.SCMain;
import net.shadowclient.main.event.events.ClipAtLedgeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "clipAtLedge", at = @At("HEAD"), cancellable = true)
    private void onClipAtLedge(CallbackInfoReturnable<Boolean> cir) {
        if (!getWorld().isClient) {
            return;
        }

        ClipAtLedgeEvent evt = new ClipAtLedgeEvent();
        SCMain.fireEvent(evt);
        if (evt.clip != 0) {
            cir.setReturnValue(evt.clip == 2);
        }
    }
}
