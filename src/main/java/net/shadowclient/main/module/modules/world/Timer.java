package net.shadowclient.main.module.modules.world;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.NumberSetting;

@SearchTags({"timer", "timer hack", "timerhack", "speedup", "speed up"})
public class Timer extends Module {

    public final NumberSetting MULTIPLIER = new NumberSetting("Multiplier", 1f, 10f, 2f, 0.1f);

    public Timer() {
        super("timer", "Timer", "Your entire game just runs faster. SPEED!!", ModuleCategory.WORLD);

        addSetting(MULTIPLIER);
    }

    public float getMultiplier() {
        return this.enabled ? MULTIPLIER.floatValue() : 1f;
    }
}
