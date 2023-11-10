package net.shadowclient.main.module.modules.render;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@SearchTags({"no wobble", "nowobble", "no lerp", "no portal wobble", "anti nausea", "no nausea"})
public class NoWobble extends Module {
    public NoWobble() {
        super("nowobble", "No Wobble", "Stops the wobble from nausea and portals.", ModuleCategory.RENDER);
    }
}
