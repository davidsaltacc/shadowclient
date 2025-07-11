package net.justacoder.shadowclient.main.setting.settings;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.translations.TranslatableString;

public class ButtonSetting extends Setting {

    private Runnable callback;

    public ButtonSetting(TranslatableString name, Runnable callback) {
        super(name);
        this.callback = callback;
    }

    public void press() {
        callback.run();
    }

    @Override
    public void reset() {}

    @Override
    public JsonObject writeConfig() {
        return null;
    }

    @Override
    public void readConfig(JsonObject in) {}
}
