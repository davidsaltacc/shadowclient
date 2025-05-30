package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;

public class NoFog extends Module {

    public NoFog() {
        super("nofog", ModuleCategory.RENDER, new String[]{"no fog", "nofog", "anti fog", "antifog"});
        addSetting(SKY);
    }

    public final BooleanSetting SKY = new BooleanSetting("Remove Sky Fog too", false);

}
