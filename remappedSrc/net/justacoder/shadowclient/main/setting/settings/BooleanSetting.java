package net.justacoder.shadowclient.main.setting.settings;

import net.justacoder.shadowclient.main.setting.Setting;

public class BooleanSetting extends Setting {

    public BooleanSetting(String name, boolean defaultValue) {
        super(name);
        boolValue = defaultValue;
    }

    private boolean boolValue;

    public boolean booleanValue() {
        return boolValue;
    }

    public void setBooleanValue(boolean value) {
        boolean old = boolValue;
        this.boolValue = value;
        callCallbacks(value, old);
    }

}
