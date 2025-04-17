package net.justacoder.shadowclient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.util.Hand;
import net.justacoder.shadowclient.main.module.ModuleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {

    @Shadow
    private boolean caughtFish;

    @Inject(method = "onTrackedDataSet", at = @At("TAIL"))
    private void injected(TrackedData<?> data, CallbackInfo ci) {
        if (caughtFish && ModuleManager.AutoFishModule.enabled) {
            ModuleManager.AutoFishModule.setRecastRodCountdown(20);
            MinecraftClient mc = MinecraftClient.getInstance();
            mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
        }
    }

}
