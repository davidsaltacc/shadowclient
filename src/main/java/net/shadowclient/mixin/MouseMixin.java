package net.shadowclient.mixin;

import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import net.shadowclient.main.module.ModuleManager;
import net.shadowclient.main.module.modules.render.Freecam;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Mouse.class)
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
}
