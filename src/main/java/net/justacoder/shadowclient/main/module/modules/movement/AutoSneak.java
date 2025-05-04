package net.justacoder.shadowclient.main.module.modules.movement;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;

public class AutoSneak extends Module {

    public AutoSneak() {

        super("autosneak", ModuleCategory.MOVEMENT, new String[]{"auto sneak", "autosneak", "auto crouch", "autocrouch"});

        addSetting(serverSideOnly);

    }

    public final BooleanSetting serverSideOnly = new BooleanSetting("Server Only", false);

}
