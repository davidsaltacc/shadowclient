package net.justacoder.shadowclient.main.module.modules.movement;

import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;

@SearchTags({"high jump", "super jump", "highjump"})
public class HighJump extends Module {

    NumberSetting HEIGHT = new NumberSetting("Height", 0, 10, 2, 1);

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
