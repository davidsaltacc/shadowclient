package net.justacoder.shadowclient.main.module.modules.menus;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.annotations.NotKeybindable;
import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@NotKeybindable
@OneClick
@SearchTags({"show settings", "options menu"})
public class ShowSettings extends Module {
    public ShowSettings() {
        super("showsettings", ModuleCategory.MENUS);
    }

    @Override
    public void onEnable() {
        mc.setScreen(ShadowClientMain.settingsGui);
        super.onEnable();
    }
}
