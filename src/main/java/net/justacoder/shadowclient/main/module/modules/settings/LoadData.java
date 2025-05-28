package net.justacoder.shadowclient.main.module.modules.settings;

import net.justacoder.shadowclient.main.annotations.Hidden;
import net.justacoder.shadowclient.main.annotations.NoSettingsScreen;
import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.config.Config;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@Hidden
@OneClick
@NoSettingsScreen
public class LoadData extends Module {
    public LoadData() {
        super("loaddata", ModuleCategory.OTHER, new String[]{"load data", "load settings"});
    }

    @Override
    public void onEnable() {
        try {
            Config.loadConfig();
        } catch (Exception ignored) {}
        super.onEnable();
    }
}
