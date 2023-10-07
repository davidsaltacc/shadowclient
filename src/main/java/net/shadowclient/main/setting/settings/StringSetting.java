package net.shadowclient.main.setting.settings;

import net.shadowclient.main.setting.Setting;

public class StringSetting extends Setting {

    private String stringValue;

    public StringSetting(String name) {
        super(name);
        this.stringValue = "";
    }

    public String stringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
}
