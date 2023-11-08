package net.shadowclient.main.module.modules.settings;

import net.shadowclient.main.annotations.Hidden;
import net.shadowclient.main.annotations.OneClick;
import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.config.Config;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@Hidden
@OneClick
@ReceiveNoUpdates
@SearchTags({"save settings", "store settings"})
public class SaveData extends Module {

    public SaveData() {
        super("savedata", "Save Settings", ModuleCategory.OTHER);
    }

    @Override
    public void OnEnable() {
        Config.saveConfig();
    }
}