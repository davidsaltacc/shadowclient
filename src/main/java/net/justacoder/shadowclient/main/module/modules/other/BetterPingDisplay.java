package net.justacoder.shadowclient.main.module.modules.other;

import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@SearchTags({"better ping display", "betterpingdisplay", "ms ping"})
public class BetterPingDisplay extends Module {
    public BetterPingDisplay() {
        super("betterpingdisplay", "Better Ping Display", "Shows the ping in MS.", ModuleCategory.OTHER);
    }

}
