package net.justacoder.shadowclient.mixin;

import net.minecraft.client.gui.screen.DeathScreen;
import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.event.events.DeathEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathScreen.class)
public abstract class DeathScreenMixin {
    @Inject(at = @At(value = "TAIL"), method = "init")
    private void injected(CallbackInfo ci) {
        EventManager.fireEvent(new DeathEvent(SCMain.mc.player == null ? null : SCMain.mc.player.getPos()));
    }
}
