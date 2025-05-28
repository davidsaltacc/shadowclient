package net.justacoder.shadowclient.main.module.modules.player.cheststeal;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.util.MathUtils;

public class ChestSteal extends Module {

    public NumberSetting DELAY = new NumberSetting("Delay", 0, 200, 10, 0, MathUtils.Easing.EASE_IN_OUT_QUADRATIC);

    public ChestSteal() {
        super("cheststeal", ModuleCategory.PLAYER, new String[]{"chest steal", "cheststeal"});

        addSetting(DELAY);
    }

    public int getDelay() {
        return DELAY.intValueEased();
    }
}
