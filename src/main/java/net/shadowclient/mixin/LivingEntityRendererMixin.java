package net.shadowclient.mixin;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.shadowclient.main.module.ModuleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Inject(method = "shouldFlipUpsideDown", at = @At("HEAD"), cancellable = true)
    private static void onShouldFlipUpsideDown(CallbackInfoReturnable<Boolean> cir) {
        if (ModuleManager.DinnerbonifyAllModule.enabled) {
            cir.setReturnValue(true);
        }
    }
}
