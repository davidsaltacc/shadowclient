package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.events.KeyEvent;
import net.minecraft.client.Keyboard;
import net.minecraft.client.input.KeyInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {

    @Inject(method = "onKey", at = @At("HEAD"))
    private void onKey(long window, int action, KeyInput input, CallbackInfo ci) {
        new KeyEvent(input.key(), input.scancode(), action, input.modifiers()).fire();
    }

}
