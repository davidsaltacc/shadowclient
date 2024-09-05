package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@SearchTags({"NoBob", "no view bobbing", "anti bobbing"})
public class NoBob extends Module {

    public NoBob() {
        super("nobob", ModuleCategory.RENDER);
    }

}
