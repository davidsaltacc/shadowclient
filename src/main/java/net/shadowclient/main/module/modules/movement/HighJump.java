package net.shadowclient.main.module.modules.movement;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.NumberSetting;

@SearchTags({"high jump", "super jump", "highjump"})
public class HighJump extends Module {

    NumberSetting HEIGHT = new NumberSetting("Height", 0, 10, 2, 0.1);

    public HighJump() {
        super("highjump", "High Jump", "You jump higher.", ModuleCategory.MOVEMENT);

        addSetting(HEIGHT);
    }

    public float increase(float in) {
        if (!enabled) {
            return in;
        }
        return in * HEIGHT.floatValue();
    }
}
