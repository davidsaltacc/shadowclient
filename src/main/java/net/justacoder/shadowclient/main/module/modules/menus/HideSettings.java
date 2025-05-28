package net.justacoder.shadowclient.main.module.modules.menus;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.annotations.Hidden;
import net.justacoder.shadowclient.main.annotations.NoSettingsScreen;
import net.justacoder.shadowclient.main.annotations.NotKeybindable;
import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@NotKeybindable
@Hidden
@OneClick
@NoSettingsScreen
public class HideSettings extends Module {
    public HideSettings() {
        super("hidesettings", ModuleCategory.MENUS, new String[]{"hide settings", "hide options menu"});
    }

    @Override
    public void onEnable() {
        mc.setScreen(ShadowClientMain.clickGui);
        super.onEnable();
    }
}
