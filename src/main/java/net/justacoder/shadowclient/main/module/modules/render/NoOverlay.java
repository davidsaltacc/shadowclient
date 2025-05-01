package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

public class NoOverlay extends Module {
    public NoOverlay() {
        super("nooverlay", ModuleCategory.RENDER, new String[]{"nooverlay", "no overlay", "no water overlay", "no lava overlay", "water vision", "lava vision"});
    }
}
