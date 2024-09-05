package net.justacoder.shadowclient.mixin;

import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.module.modules.render.Freecam;
import net.justacoder.shadowclient.main.ui.notifications.NotificationsManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value = Mouse.class)
public abstract class MouseMixin {

    @ModifyArgs(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"))
    private void onLookDirection(Args args) {

        Freecam freecam = ModuleManager.FreecamModule;

        if (freecam.enabled) {
            freecam.lookDirection((double) args.get(0) * 0.15, (double) args.get(1) * 0.15);
            args.set(0, 0d);
            args.set(1, 0d);
        }

    }

    @SuppressWarnings("InvalidInjectorMethodSignature")
    @Inject(method = "onMouseButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;applyMousePressScrollNarratorDelay()V"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onMouseClickedCalled(long window, int button, int action, int mods, CallbackInfo ci, boolean bl, int i, boolean[] bls, double d, double e, Screen screen) { // apparently this is incorrect. but if I change it, it breaks.
        NotificationsManager.mouseClicked(d, e, button);
    }
}
