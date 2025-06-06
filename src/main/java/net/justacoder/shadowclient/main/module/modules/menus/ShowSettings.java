package net.justacoder.shadowclient.main.module.modules.menus;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.annotations.NoSettingsScreen;
import net.justacoder.shadowclient.main.annotations.NotKeybindable;
import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@NotKeybindable
@OneClick
@NoSettingsScreen
public class ShowSettings extends Module {
    public ShowSettings() {
        super("showsettings", ModuleCategory.MENUS, new String[]{"show settings", "options menu"});
    }

    @Override
    public boolean onEnable() {
        mc.setScreen(ShadowClientMain.settingsGui);
        return super.onEnable();
    }
}
