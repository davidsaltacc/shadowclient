package net.shadowclient.main.module.modules.other;

import net.shadowclient.main.annotations.OneClick;
import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.config.ConfigFiles;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@OneClick
@ReceiveNoUpdates
@SearchTags({"save settings", "store settings"})
public class SaveData extends Module {

    public SaveData() {
        super("savedata", "Save Settings", ModuleCategory.OTHER);
    }

    @Override
    public void OnEnable() {
        ConfigFiles.saveConfig();
    }
}
