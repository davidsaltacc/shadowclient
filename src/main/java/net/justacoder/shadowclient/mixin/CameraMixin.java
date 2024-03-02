package net.justacoder.shadowclient.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
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

    @ModifyArgs(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setPos(DDD)V"))
    private void onUpdateSetPosArgs(Args args) {
        Freecam freecam = ModuleManager.FreecamModule;

        if (freecam.enabled) {
            args.set(0, freecam.getX());
            args.set(1, freecam.getY());
            args.set(2, freecam.getZ());
        }
    }

    @ModifyArgs(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V"))
    private void onUpdateSetRotationArgs(Args args) {
        Freecam freecam = ModuleManager.FreecamModule;

        if (freecam.enabled) {
            args.set(0, (float) freecam.getYaw());
            args.set(1, (float) freecam.getPitch());
        }
    }

    @Inject(method = "getSubmersionType", at = @At("HEAD"), cancellable = true)
    private void onGetSubmersionType(CallbackInfoReturnable<CameraSubmersionType> cir) {
        if (ModuleManager.NoOverlayModule.enabled) {
            cir.setReturnValue(CameraSubmersionType.NONE);
        }
    }
}
