package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.event.events.MouseClickedEvent;
import net.justacoder.shadowclient.main.event.events.MouseMoveEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.Screen;
import net.justacoder.shadowclient.main.module.modules.render.Freecam;
import net.justacoder.shadowclient.main.ui.notifications.NotificationsManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value = Mouse.class)
public abstract class MouseMixin {

    @Shadow @Final private MinecraftClient client;

    @ModifyArgs(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"))
    private void onLookDirection(Args args) {

        MouseMoveEvent event = new MouseMoveEvent((double) args.get(0) * 0.15, (double) args.get(1) * 0.15);
        EventManager.fireEvent(event);
        if (event.cancelled) {
            args.set(0, 0.);
            args.set(1, 0.);
        } else {
            args.set(0, event.getDeltaX() * (1 / 0.15));
            args.set(1, event.getDeltaY() * (1 / 0.15));
        }

    }

    @Inject(method = "onMouseButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;applyMousePressScrollNarratorDelay()V"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onMouseClickedCalled(long window, int button, int action, int mods, CallbackInfo ci, boolean bl, int i, double d, double e, Screen screen) { // apparently this is incorrect. but if I change it, it breaks.
        NotificationsManager.mouseClicked(d, e, button);
    }

    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    private void fireMouseClickEvent(long window, int button, int action, int mods, CallbackInfo ci) {
        if (window == client.getWindow().getHandle() && action == 1) {
            MouseClickedEvent event = new MouseClickedEvent(button);
            EventManager.fireEvent(event);
            if (event.cancelled) {
                ci.cancel();
            }
        }
    }

}
