package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

public class NoTiltOnHurt extends Module {
    public NoTiltOnHurt() {
        super("notiltonhurt", ModuleCategory.RENDER, new String[]{"no tilt on hurt", "no hurt", "on hurt no tilt", "no tilt when damage", "no damage tilt"});
    }
}
