package net.justacoder.shadowclient.main.setting.settings;

import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.translations.TranslatableString;

public class StringSetting extends Setting {

    private String stringValue;

    public StringSetting(TranslatableString name) {
        super(name);
        this.stringValue = "";
    }

    public String stringValue() {
        return stringValue;
    }

    public void setStringValue(String value) {
        String old = stringValue;
        this.stringValue = value;
        callCallbacks(value, old);
    }
}
