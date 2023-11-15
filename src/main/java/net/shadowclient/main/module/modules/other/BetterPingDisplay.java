package net.shadowclient.main.module.modules.other;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@SearchTags({"better ping display", "betterpingdisplay", "ms ping"})
public class BetterPingDisplay extends Module { // TODO test and fix, might not look good
    public BetterPingDisplay() {
        super("betterpingdisplay", "Better Ping Display", "Shows the ping in MS.", ModuleCategory.OTHER);
    }

}
