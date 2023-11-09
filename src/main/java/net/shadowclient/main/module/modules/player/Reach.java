package net.shadowclient.main.module.modules.player;

import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.NumberSetting;

@ReceiveNoUpdates
@SearchTags({"reach", "reachhack", "reach hack"})
public class Reach extends Module {

    NumberSetting RANGE = new NumberSetting("Range", 1, 50, 10, 0.1);

    public Reach() {
        super("reach", "Reach", "Extended reach.", ModuleCategory.PLAYER);

        addSetting(RANGE);
    }

    public float distance() {
        return RANGE.floatValue();
    }
}
