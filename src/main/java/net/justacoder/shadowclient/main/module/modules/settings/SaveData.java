package net.justacoder.shadowclient.main.module.modules.settings;

import net.justacoder.shadowclient.main.annotations.Hidden;
import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.config.Config;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@Hidden
@OneClick
public class SaveData extends Module {

    public SaveData() {
        super("savedata", ModuleCategory.OTHER, new String[]{"save settings", "store settings"});
    }

    @Override
    public void onEnable() {
        Config.saveConfig();
        super.onEnable();
    }
}
