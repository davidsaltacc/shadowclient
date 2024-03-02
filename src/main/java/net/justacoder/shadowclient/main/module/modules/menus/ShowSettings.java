package net.justacoder.shadowclient.main.module.modules.menus;

import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@OneClick
@SearchTags({"show settings", "options menu"})
public class ShowSettings extends Module {
    public ShowSettings() {
        super("showsettings", "Settings Menu", "Shows the hack client settings menu.", ModuleCategory.MENUS);
    }

    @Override
    public void onEnable() {
        mc.setScreen(SCMain.settingsGui);
        super.onEnable();
    }
}
