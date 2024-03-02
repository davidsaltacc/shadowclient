package net.justacoder.shadowclient.main.module.modules.player.cheststeal;

import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;

@SearchTags({"chest steal", "cheststeal"})
public class ChestSteal extends Module {

    public NumberSetting DELAY = new NumberSetting("Delay", 0, 200, 10, 0);

    public ChestSteal() {
        super("cheststeal", "Chest Steal", "Adds a button to the chest UI to steal all contents. ", ModuleCategory.PLAYER);

        addSetting(DELAY);
    }

    public int getDelay() {
        return DELAY.intValue();
    }
}
