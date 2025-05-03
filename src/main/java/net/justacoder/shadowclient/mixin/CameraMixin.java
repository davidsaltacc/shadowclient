package net.justacoder.shadowclient.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.block.enums.CameraSubmersionType;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.module.modules.render.ExtendedCameraDistance;
import net.justacoder.shadowclient.main.module.modules.render.Freecam;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Inject(at = @At("HEAD"), method = "clipToSpace", cancellable = true)
    private void onClipToSpace(float desiredCameraDistance, CallbackInfoReturnable<Float> cir) {
        if (ModuleManager.CameraNoclipModule.enabled) {
            cir.setReturnValue(desiredCameraDistance);
        }
    }

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;clipToSpace(F)F"))
    private float onClipToSpace(float desiredCameraDistance) {
        ExtendedCameraDistance ecd = ModuleManager.ExtendedCameraDistanceModule;
        if (ecd.enabled) {
            return ecd.getDistance();
        }
        return desiredCameraDistance;
    }

    @Inject(method = "getSubmersionType", at = @At("HEAD"), cancellable = true)
    private void onGetSubmersionType(CallbackInfoReturnable<CameraSubmersionType> cir) {
        if (ModuleManager.NoOverlayModule.enabled) {
            cir.setReturnValue(CameraSubmersionType.NONE);
        }
    }
}
