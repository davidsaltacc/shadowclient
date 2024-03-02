package net.justacoder.shadowclient.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.event.events.KeyPressEvent;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {

    @Inject(at = @At("HEAD"), method = "onKey(JIIII)V", cancellable = true)
    private void injected(long windowHandle, int keyCode, int scanCode, int action, int modifiers, CallbackInfo ci) {

        KeyPressEvent evt = new KeyPressEvent(keyCode, scanCode, action, modifiers);

        EventManager.fireEvent(evt);

        if (evt.cancelled) {
            ci.cancel();
        }
    }

    @Redirect(method = "onKey", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;currentScreen:Lnet/minecraft/client/gui/screen/Screen;", opcode = Opcodes.GETFIELD))
    private Screen redirect(MinecraftClient instance) {
        return SCMain.allowKeyPress(instance.currentScreen);
    }

}