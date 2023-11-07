package net.shadowclient.mixin;

import net.minecraft.client.render.Camera;
import net.shadowclient.main.module.ModuleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Inject(at = @At("HEAD"), method = "clipToSpace(D)D", cancellable = true)
    private void onClipToSpace(double desiredCameraDistance, CallbackInfoReturnable<Double> cir) {
        if (ModuleManager.CameraNoclipModule.enabled) {
            cir.setReturnValue(desiredCameraDistance);
        }
    }
}
