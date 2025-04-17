package net.justacoder.shadowclient.mixin;

import net.minecraft.block.PowderSnowBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.justacoder.shadowclient.main.module.ModuleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public abstract class PowderSnowBlockMixin {

    @Inject(at = @At("HEAD"), method = "canWalkOnPowderSnow", cancellable = true)
    private static void injected(Entity entity, CallbackInfoReturnable<Boolean> cir)
    {
        if (!ModuleManager.PowderSnowWalkModule.enabled) {
            return;
        }

        if (entity != MinecraftClient.getInstance().player) {
            return;
        }

        cir.setReturnValue(true);
    }
}
