package net.shadowclient.main.module.modules.render;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@SearchTags({"no blind", "anti blind", "antiblind", "anti darkness", "anti warden"})
public class NoBlind extends Module {
    public NoBlind() {
        super("noblind", "No Blind", "Allows you to see through blindness and darkness.", ModuleCategory.RENDER);
    }
}
