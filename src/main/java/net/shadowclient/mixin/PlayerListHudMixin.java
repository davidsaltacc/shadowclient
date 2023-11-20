package net.shadowclient.mixin;

import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;
import net.shadowclient.main.module.ModuleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(PlayerListHud.class)
public abstract class PlayerListHudMixin {

    @Redirect(method = "getPlayerName", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/PlayerListHud;applyGameModeFormatting(Lnet/minecraft/client/network/PlayerListEntry;Lnet/minecraft/text/MutableText;)Lnet/minecraft/text/Text;"))
    private Text onApplyGameModeFormatting(PlayerListHud instance, PlayerListEntry entry, MutableText name) {

        if (ModuleManager.BetterPingDisplayModule.enabled) {
            name = MutableText.of(Text.of("[" + entry.getLatency() + "ms] " + name.getString()).getContent());
        }

        return entry.getGameMode() == GameMode.SPECTATOR ? name.formatted(Formatting.ITALIC) : name;
    }

}
