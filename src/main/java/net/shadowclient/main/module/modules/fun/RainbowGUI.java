package net.shadowclient.main.module.modules.fun;

import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@ReceiveNoUpdates
@SearchTags({"rainbow gui", "rainbowgui", "rainbow ui", "jeb_ ui"})
public class RainbowGUI extends Module {
    public RainbowGUI() {
        super("rainbowgui", "Rainbow GUI", "Makes the UI instead of being black, go to rainbow.", ModuleCategory.FUN);
    }
}
