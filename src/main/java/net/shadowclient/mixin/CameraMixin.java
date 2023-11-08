package net.shadowclient.mixin;

import net.minecraft.client.render.Camera;
import net.shadowclient.main.module.ModuleManager;
import net.shadowclient.main.module.modules.render.ExtendedCameraDistance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Inject(at = @At("HEAD"), method = "clipToSpace(D)D", cancellable = true)
    private void onClipToSpace(double desiredCameraDistance, CallbackInfoReturnable<Double> cir) {
        if (ModuleManager.CameraNoclipModule.enabled) {
            cir.setReturnValue(desiredCameraDistance);
        }
    }

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;clipToSpace(D)D"))
    private double onClipToSpace(double desiredCameraDistance) {
        ExtendedCameraDistance ecd = ModuleManager.ExtendedCameraDistanceModule;
        if (ecd.enabled) {
            return ecd.getDistance();
        }
        return desiredCameraDistance;
    }
}
