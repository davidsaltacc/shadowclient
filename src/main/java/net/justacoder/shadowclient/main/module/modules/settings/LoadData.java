package net.justacoder.shadowclient.main.module.modules.settings;

import net.justacoder.shadowclient.main.annotations.Hidden;
import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.config.Config;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@Hidden
@OneClick
@SearchTags({"load data", "load settings"})
public class LoadData extends Module {
    public LoadData() {
        super("loaddata", "Load Settings", "Loads the saved configuration from the settings file.", ModuleCategory.OTHER);
    }

    @Override
    public void onEnable() {
        try {
            Config.loadConfig();
        } catch (Exception ignored) {}
        super.onEnable();
    }
}
