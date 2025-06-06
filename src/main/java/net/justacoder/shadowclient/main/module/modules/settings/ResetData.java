package net.justacoder.shadowclient.main.module.modules.settings;

import net.justacoder.shadowclient.main.annotations.Hidden;
import net.justacoder.shadowclient.main.annotations.NoSettingsScreen;
import net.justacoder.shadowclient.main.annotations.NotKeybindable;
import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.config.Config;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@NotKeybindable
@Hidden
@OneClick
@NoSettingsScreen
public class ResetData extends Module {
    public ResetData() {
        super("resetdata", ModuleCategory.OTHER, new String[]{"delete data", "remove settings", "reset"});
    }

    @Override
    public boolean onEnable() {
        Config.resetConfig();
        return super.onEnable();
    }
}
