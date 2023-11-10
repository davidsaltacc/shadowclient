package net.shadowclient.main.module.modules.menus;

import net.shadowclient.main.SCMain;
import net.shadowclient.main.annotations.Hidden;
import net.shadowclient.main.annotations.OneClick;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@Hidden
@OneClick
@SearchTags({"hide settings", "hide options menu"})
public class HideSettings extends Module {
    public HideSettings() {
        super("hidesettings", "Back", "Hides the hack client settings menu.", ModuleCategory.MENUS);
    }

    @Override
    public void OnEnable() {
        mc.setScreen(SCMain.clickGui);
    }
}
