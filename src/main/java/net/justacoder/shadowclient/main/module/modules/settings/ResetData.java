package net.justacoder.shadowclient.main.module.modules.settings;

import net.justacoder.shadowclient.main.annotations.Hidden;
import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.config.Config;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@Hidden
@OneClick
@SearchTags({"delete data", "remove settings", "reset"})
public class ResetData extends Module {
    public ResetData() {
        super("resetdata", "Reset Settings", "Erases all the settings.", ModuleCategory.OTHER);
    }

    @Override
    public void onEnable() {
        Config.resetConfig();
        super.onEnable();
    }
}
