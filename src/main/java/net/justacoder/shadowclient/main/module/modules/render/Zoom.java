package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.annotations.DoNotSaveState;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.translations.TranslatableString;

@DoNotSaveState
public class Zoom extends Module {

    public NumberSetting FOV = new NumberSetting(TranslatableString.of("setting.module.shadowclient.zoom.fov"), 1f, 179.9f, 90f, 0);

    public Zoom() {
        super("zoom", ModuleCategory.RENDER, new String[]{"zoom", "fov", "increase fov", "decrease fov", "increase zoom", "decrease zoom", "zoom in", "zoom out"});

        addSettings(FOV);
    }

}
