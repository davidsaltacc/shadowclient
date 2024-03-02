package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@SearchTags({"no levitation", "anti levitation", "anti shulker"})
public class NoLevitation extends Module {
    public NoLevitation() {
        super("nolevitation", "No Levitation", "Stops levitation from working. ", ModuleCategory.RENDER);
    }
}
