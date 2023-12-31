package net.shadowclient.main.setting.settings;

import net.shadowclient.main.setting.Setting;

public class NumberSetting extends Setting {

    public final int decimalPlaces;

    public NumberSetting(String name, Number min, Number max, Number defaultValue, int decimalPlaces) {
        super(name, min, max, defaultValue);
        this.decimalPlaces = decimalPlaces;
    }
}
