package net.shadowclient.main.setting.settings;

import net.shadowclient.main.setting.Setting;

public class NumberSetting extends Setting {

    public final Number increment;

    public NumberSetting(String name, Number min, Number max, Number defaultValue, Number increment) {
        super(name, min, max, defaultValue);
        this.increment = increment;
    }
}
