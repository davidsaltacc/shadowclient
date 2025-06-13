package net.justacoder.shadowclient.main.module.modules.movement;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.translations.TranslatableString;

public class HighJump extends Module {

    NumberSetting HEIGHT = new NumberSetting(new TranslatableString("setting.module.shadowclient.highjump.height"), 0, 10, 2, 1);

    public HighJump() {
        super("highjump", ModuleCategory.MOVEMENT, new String[]{"high jump", "super jump", "highjump"});

        addSetting(HEIGHT);
    }

    public float increase(float in) {
        if (!enabled) {
            return in;
        }
        return in * HEIGHT.floatValueEased();
    }
}
