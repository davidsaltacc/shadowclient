package net.shadowclient.main.module.modules.render;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@SearchTags({"no levitation", "anti levitation", "anti shulker"})
public class NoLevitation extends Module {
    public NoLevitation() {
        super("nolevitation", "No Levitation", "Stops levitation from working. ", ModuleCategory.RENDER);
    }
}
