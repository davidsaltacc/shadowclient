package net.shadowclient.main.module.modules.menus;

import net.shadowclient.main.SCMain;
import net.shadowclient.main.annotations.OneClick;
import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@ReceiveNoUpdates
@OneClick
@SearchTags({"show settings", "options menu"})
public class ShowSettings extends Module {
    public ShowSettings() {
        super("showsettings", "Settings Menu", ModuleCategory.MENUS);
    }

    @Override
    public void OnEnable() {
        mc.setScreen(SCMain.settingsGui);
    }
}
