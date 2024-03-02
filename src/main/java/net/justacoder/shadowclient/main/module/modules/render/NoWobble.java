package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@SearchTags({"no wobble", "nowobble", "no lerp", "no portal wobble", "anti nausea", "no nausea"})
public class NoWobble extends Module {
    public NoWobble() {
        super("nowobble", "No Wobble", "Stops the wobble from nausea and portals.", ModuleCategory.RENDER);
    }
}
