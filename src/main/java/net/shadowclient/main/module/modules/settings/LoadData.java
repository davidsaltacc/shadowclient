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
@SearchTags({"load data", "load settings"})
public class LoadData extends Module {
    public LoadData() {
        super("loaddata", "Load Settings", ModuleCategory.OTHER);
    }

    @Override
    public void OnEnable() {
        Config.loadConfig();
    }
}
