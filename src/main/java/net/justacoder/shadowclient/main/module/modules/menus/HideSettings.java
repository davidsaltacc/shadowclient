package net.justacoder.shadowclient.main.module.modules.menus;

import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.annotations.Hidden;
import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@Hidden
@OneClick
@SearchTags({"hide settings", "hide options menu"})
public class HideSettings extends Module {
    public HideSettings() {
        super("hidesettings", "Back", "Hides the hack client settings menu.", ModuleCategory.MENUS);
    }

    @Override
    public void onEnable() {
        mc.setScreen(SCMain.clickGui);
        super.onEnable();
    }
}
