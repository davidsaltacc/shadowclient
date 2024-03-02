package net.justacoder.shadowclient.main.module.modules.player;

import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;

@SearchTags({"reach", "reachhack", "reach hack"})
public class Reach extends Module {

    NumberSetting RANGE = new NumberSetting("Range", 1, 50, 10, 1);

    public Reach() {
        super("reach", "Reach", "Extended reach.", ModuleCategory.PLAYER);

        addSetting(RANGE);
    }

    public float distance() {
        return RANGE.floatValue();
    }
}
