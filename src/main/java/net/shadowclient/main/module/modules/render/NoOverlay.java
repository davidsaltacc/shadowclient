package net.shadowclient.main.module.modules.render;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@SearchTags({"nooverlay", "no overlay", "no water overlay", "no lava overlay", "water vision", "lava vision"})
public class NoOverlay extends Module {
    public NoOverlay() {
        super("nooverlay", "No Overlay", "Disabled the screen from turning blue in water.", ModuleCategory.RENDER);
    }
}
