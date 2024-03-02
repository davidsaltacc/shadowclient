package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@SearchTags({"seebarriers", "render barriers", "see barrier blocks", "anti barrier"})
public class RenderBarriers extends Module {
    public RenderBarriers() {
        super("seebarriers", "See Barriers", "Renders barrier blocks visibly.", ModuleCategory.RENDER);
    }
}
