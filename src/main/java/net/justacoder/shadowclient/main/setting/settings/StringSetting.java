package net.justacoder.shadowclient.main.setting.settings;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.translations.TranslatableString;

public class StringSetting extends Setting {

    private String stringValue;
    private String defaultValue;

    public StringSetting(TranslatableString name) {
        super(name);
        this.stringValue = "";
        this.defaultValue = "";
    }

    public StringSetting(TranslatableString name, String defaultValue) {
        super(name);
        this.stringValue = defaultValue;
        this.defaultValue = defaultValue;
    }

    public String stringValue() {
        return stringValue;
    }

    public void setStringValue(String value) {
        String old = stringValue;
        this.stringValue = value;
        callCallbacks(value, old);
    }

    @Override
    public void reset() {
        setStringValue(defaultValue);
    }

    @Override
    public JsonObject writeConfig() {
        JsonObject object = new JsonObject();
        object.addProperty("value", stringValue);
        return object;
    }

    @Override
    public void readConfig(JsonObject in) {
        stringValue = in.get("value").getAsString();
    }
}
