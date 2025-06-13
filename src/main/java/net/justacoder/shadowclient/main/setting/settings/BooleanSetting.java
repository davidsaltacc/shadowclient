package net.justacoder.shadowclient.main.setting.settings;

import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.translations.TranslatableString;

public class BooleanSetting extends Setting {

    public BooleanSetting(TranslatableString name, boolean defaultValue) {
        super(name);
        boolValue = defaultValue;
    }

    private boolean boolValue;

    public boolean booleanValue() {
        return boolValue;
    }

    public void setBooleanValue(boolean value, boolean callCallbacks) {
        boolean old = boolValue;
        this.boolValue = value;
        if (callCallbacks) {
            callCallbacks(value, old);
        }
    }

    public void setBooleanValue(boolean value) {
        setBooleanValue(value, true);
    }

}
