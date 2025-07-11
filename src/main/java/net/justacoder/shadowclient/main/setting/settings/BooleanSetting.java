package net.justacoder.shadowclient.main.setting.settings;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.translations.TranslatableString;

public class BooleanSetting extends Setting {

    public BooleanSetting(TranslatableString name, boolean defaultValue) {
        super(name);
        this.boolValue = defaultValue;
        this.defaultValue = defaultValue;
    }

    private boolean boolValue;
    private boolean defaultValue;

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

    @Override
    public void reset() {
        setBooleanValue(defaultValue);
    }

    @Override
    public JsonObject writeConfig() {
        JsonObject object = new JsonObject();
        object.addProperty("value", boolValue);
        return object;
    }

    @Override
    public void readConfig(JsonObject in) {
        boolValue = in.get("value").getAsBoolean();
    }
}
