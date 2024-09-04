package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.annotations.DoNotSaveState;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;

@DoNotSaveState
@SearchTags({"zoom", "fov", "increase fov", "decrease fov", "increase zoom", "decrease zoom"})
public class Zoom extends Module {

    public NumberSetting FOV = new NumberSetting("FOV", 1f, 179f, 90f, 0);

    public Zoom() {
        super("zoom", ModuleCategory.RENDER);

        addSettings(FOV);
    }

}
