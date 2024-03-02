package net.justacoder.shadowclient.main.module.modules.settings;

import net.justacoder.shadowclient.main.annotations.Hidden;
import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.config.Config;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@Hidden
@OneClick
@SearchTags({"save settings", "store settings"})
public class SaveData extends Module {

    public SaveData() {
        super("savedata", "Save Settings", "Saves the current configuration to the settings file.", ModuleCategory.OTHER);
    }

    @Override
    public void onEnable() {
        Config.saveConfig();
        super.onEnable();
    }
}
