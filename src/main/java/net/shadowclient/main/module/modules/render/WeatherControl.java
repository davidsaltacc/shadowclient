package net.shadowclient.main.module.modules.render;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.BooleanSetting;
import net.shadowclient.main.setting.settings.NumberSetting;

@SearchTags({"time control", "weather control"})
public class WeatherControl extends Module {

    public BooleanSetting DISABLE_RAIN = new BooleanSetting("Disable Rain", true);
    public BooleanSetting CHANGE_TIME = new BooleanSetting("Change Daytime", false);
    public BooleanSetting CHANGE_MOON = new BooleanSetting("Change Moon Phase", false);
    public NumberSetting TIME = new NumberSetting("Daytime", 0, 23999, 6000, 0);
    public NumberSetting MOON = new NumberSetting("Moon Phase", 0, 7, 0, 0);


    public WeatherControl() {
        super("weathercontrol", "Weather Control", "Lets you change things about the weather and daytime.", ModuleCategory.RENDER);

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
        return TIME.intValue();
    }
    public int getMoon() {
        return MOON.intValue();
    }
}
