package net.shadowclient.main.module.modules.render;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@SearchTags({"seebarriers", "render barriers", "see barrier blocks", "anti barrier"})
public class RenderBarriers extends Module {
    public RenderBarriers() {
        super("seebarriers", "See Barriers", "Renders barrier blocks visibly.", ModuleCategory.RENDER);
    }
}
