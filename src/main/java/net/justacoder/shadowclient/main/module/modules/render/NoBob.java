package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

public class NoBob extends Module {

    public NoBob() {
        super("nobob", ModuleCategory.RENDER, new String[]{"NoBob", "no view bobbing", "anti bobbing"});
    }

}
