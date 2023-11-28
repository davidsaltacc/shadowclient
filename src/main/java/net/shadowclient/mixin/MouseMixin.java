package net.shadowclient.mixin;

import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.shadowclient.main.module.ModuleManager;
import net.shadowclient.main.module.modules.render.Freecam;
import net.shadowclient.main.ui.notifications.NotificationsManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = Mouse.class)
public abstract class MouseMixin {
    @Redirect(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"))
    private void onLookDirection(ClientPlayerEntity player, double cursorDeltaX, double cursorDeltaY) {
        Freecam freecam = ModuleManager.FreecamModule;

        if (freecam.enabled) {
            freecam.lookDirection(cursorDeltaX * 0.15, cursorDeltaY * 0.15);
        }
        else {
            player.changeLookDirection(cursorDeltaX, cursorDeltaY);
        }
    }

    @SuppressWarnings("InvalidInjectorMethodSignature")
    @Inject(method = "onMouseButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;applyMousePressScrollNarratorDelay()V"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onMouseClickedCalled(long window, int button, int action, int mods, CallbackInfo ci, boolean bl, int i, boolean[] bls, double d, double e, Screen screen) { // apparently this is incorrect. but if I change it, it breaks.
        NotificationsManager.mouseClicked(d, e, button);
    }
}
