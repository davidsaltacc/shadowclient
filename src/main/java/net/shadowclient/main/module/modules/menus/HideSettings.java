package net.shadowclient.main.module.modules.menus;

import net.shadowclient.main.SCMain;
import net.shadowclient.main.annotations.Hidden;
import net.shadowclient.main.annotations.OneClick;
import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@Hidden
@ReceiveNoUpdates
@OneClick
@SearchTags({"hide settings", "hide options menu"})
public class HideSettings extends Module {
    public HideSettings() {
        super("hidesettings", "Back", ModuleCategory.MENUS);
    }

    @Override
    public void OnEnable() {
        mc.setScreen(SCMain.clickGui);
    }
}
