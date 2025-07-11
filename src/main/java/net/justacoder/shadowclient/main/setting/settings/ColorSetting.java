package net.justacoder.shadowclient.main.setting.settings;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.translations.TranslatableString;

public class ColorSetting extends Setting {

    public ColorSetting(TranslatableString name, int defaultColor) {
        super(name);
        this.color = defaultColor;
        this.defaultColor = defaultColor;
    }

    private int color;
    private int defaultColor;

    public int colorValue() {
        return color;
    }

    public void setColorValue(int value) {
        int old = this.color;
        this.color = value;
        callCallbacks(value, old);
    }

    public void setColorValue(int value, boolean callCallbacks) {
        int old = this.color;
        this.color = value;
        if (callCallbacks) {
            callCallbacks(value, old);
        }
    }

    @Override
    public void reset() {
        setColorValue(defaultColor);
    }

    @Override
    public JsonObject writeConfig() {
        JsonObject object = new JsonObject();
        object.addProperty("value", color);
        return object;
    }

    @Override
    public void readConfig(JsonObject in) {
        color = in.get("value").getAsInt();
    }
}
