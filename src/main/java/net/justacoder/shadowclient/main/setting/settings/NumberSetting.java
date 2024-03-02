package net.justacoder.shadowclient.main.setting.settings;

import net.justacoder.shadowclient.main.setting.Setting;

public class NumberSetting extends Setting {

    public final int decimalPlaces;

    public NumberSetting(String name, Number min, Number max, Number defaultValue, int decimalPlaces) { // todo be able to specify if slider or number input
        super(name, min, max, defaultValue);
        this.decimalPlaces = decimalPlaces;
    }
}
