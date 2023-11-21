package net.shadowclient.main.module.modules.settings;

import net.shadowclient.main.annotations.Hidden;
import net.shadowclient.main.annotations.OneClick;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.config.Config;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

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
    }
}
