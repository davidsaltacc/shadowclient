package net.justacoder.shadowclient.main.module.modules.world;

import net.justacoder.shadowclient.main.annotations.DontSaveState;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;

@DontSaveState
@SearchTags({"timer", "timer hack", "timerhack", "speedup", "speed up"})
public class Timer extends Module {

    public final NumberSetting MULTIPLIER = new NumberSetting("Multiplier", 0.1f, 10f, 2f, 1);

    public Timer() {
        super("timer", "Timer", "Your entire game just runs faster. SPEED!!", ModuleCategory.WORLD);

        addSetting(MULTIPLIER);
    }

    public float getMultiplier() {
        return this.enabled ? MULTIPLIER.floatValue() : 1f;
    }
}
