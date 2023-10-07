package net.shadowclient.main.module.modules.other;

import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@ReceiveNoUpdates
@SearchTags({"disable", "stop", "no updates", "no ticks"})
public class DisableHackUpdates extends Module {

    public DisableHackUpdates() {
        super("disablehackupdates", "No Hack Updates", ModuleCategory.OTHER);
    }

}
