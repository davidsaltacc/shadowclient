package net.justacoder.shadowclient.main.module.modules.world;

import net.justacoder.shadowclient.main.annotations.DoNotSaveState;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.justacoder.shadowclient.main.util.MathUtils;

@DoNotSaveState
public class Timer extends Module {

    public final NumberSetting MULTIPLIER = new NumberSetting(new TranslatableString("setting.module.shadowclient.timer.multiplier"), 0.1f, 10f, 2f, 1, MathUtils.Easing.EASE_IN_QUADRATIC);

    public Timer() {
        super("timer", ModuleCategory.WORLD, new String[]{"timer", "timer hack", "timerhack", "speedup", "speed up"});

        addSetting(MULTIPLIER);
    }

    public float getMultiplier() {
        return this.enabled ? MULTIPLIER.floatValueEased() : 1f;
    }
}
