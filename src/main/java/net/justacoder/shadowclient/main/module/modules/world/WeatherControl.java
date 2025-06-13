package net.justacoder.shadowclient.main.module.modules.world;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.translations.TranslatableString;

public class WeatherControl extends Module {

    public BooleanSetting DISABLE_RAIN = new BooleanSetting(new TranslatableString("setting.module.shadowclient.weathercontrol.disable_rain"), true);
    public BooleanSetting CHANGE_TIME = new BooleanSetting(new TranslatableString("setting.module.shadowclient.weathercontrol.change_time"), false);
    public BooleanSetting CHANGE_MOON = new BooleanSetting(new TranslatableString("setting.module.shadowclient.weathercontrol.change_moon"), false);
    public NumberSetting TIME = new NumberSetting(new TranslatableString("setting.module.shadowclient.weathercontrol.daytime"), 0, 23999, 6000, 0);
    public NumberSetting MOON = new NumberSetting(new TranslatableString("setting.module.shadowclient.weathercontrol.moon_phase"), 0, 7, 0, 0);


    public WeatherControl() {
        super("weathercontrol", ModuleCategory.WORLD, new String[]{"time control", "weather control", "moon control"});

        addSettings(DISABLE_RAIN, CHANGE_TIME, CHANGE_MOON, TIME, MOON);
    }

    public boolean rainDisabled() {
        return DISABLE_RAIN.booleanValue() && enabled;
    }
    public boolean timeChanged() {
        return CHANGE_TIME.booleanValue() && enabled;
    }
    public boolean moonChanged() {
        return CHANGE_MOON.booleanValue() && enabled;
    }

    public int getTime() {
        return TIME.intValueEased();
    }
    public int getMoon() {
        return MOON.intValueEased();
    }
}
