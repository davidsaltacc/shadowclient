package net.justacoder.shadowclient.main.module.modules.player.cheststeal;

import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.util.MathUtils;

@SearchTags({"chest steal", "cheststeal"})
public class ChestSteal extends Module {

    public NumberSetting DELAY = new NumberSetting("Delay", 0, 200, 10, 0, MathUtils.Easing.EASE_IN_OUT_QUADRATIC);

    public ChestSteal() {
        super("cheststeal", ModuleCategory.PLAYER);

        addSetting(DELAY);
    }

    public int getDelay() {
        return DELAY.intValue();
    }
}
