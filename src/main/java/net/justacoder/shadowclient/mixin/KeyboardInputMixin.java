package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.module.ModuleManager;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin extends Input {

    @Inject(method = "tick", at = @At("TAIL"))
    private void forceForwardMovement(CallbackInfo ci) {
        if (ModuleManager.AutoMoveModule.enabled) {
            this.movementForward = 1f;
        }
    }

}
