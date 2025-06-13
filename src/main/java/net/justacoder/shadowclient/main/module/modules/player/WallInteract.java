package net.justacoder.shadowclient.main.module.modules.player;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;
import net.justacoder.shadowclient.main.translations.TranslatableString;

public class WallInteract extends Module {

    public WallInteract() {

        super("wallinteract", ModuleCategory.PLAYER, new String[]{"wallinteract", "wall interact", "wallhack", "attack through walls", "wallhit"});

        addSetting(disableOnSneak);

    }

    public final BooleanSetting disableOnSneak = new BooleanSetting(new TranslatableString("setting.module.shadowclient.wallinteract.sneak_disables"), true);

}
