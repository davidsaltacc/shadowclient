package net.shadowclient.main.module.modules.render;

import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@ReceiveNoUpdates
@SearchTags({"no blind", "anti blind", "antiblind", "anti darkness", "anti warden"})
public class NoBlind extends Module {
    public NoBlind() {
        super("noblind", "No Blind", ModuleCategory.RENDER);
    }
}
