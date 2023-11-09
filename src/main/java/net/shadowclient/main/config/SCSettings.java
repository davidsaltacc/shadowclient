package net.shadowclient.main.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.shadowclient.main.setting.Setting;
import net.shadowclient.main.setting.settings.BooleanSetting;
import net.shadowclient.main.setting.settings.StringSetting;
import org.jetbrains.annotations.Nullable;

public class SCSettings {

    public static final BooleanSetting VanillaSpoof = new BooleanSetting("Vanilla Spoof", true);
    public static final BooleanSetting WelcomeMessage = new BooleanSetting("Welcome Msg", true);

    public static final int LOADING_SCREEN_BGND_COLOR = -16382447; // TODO make configurable maybe

    public static @Nullable Setting getSetting(String name) {
        JsonObject settings = Config.getSCSettings();
        if (settings != null) {
            if (settings.has(name)) {
                JsonPrimitive setting = settings.getAsJsonPrimitive(name);
                try {
                    Setting settingobj = (Setting) SCSettings.class.getDeclaredField(name).get(null);
                    if (setting.isBoolean()) {
                        settingobj.setBooleanValue(setting.getAsBoolean());
                    }
                    if (setting.isNumber()) {
                        settingobj.setNumberValue(setting.getAsNumber());
                    }
                    if (setting.isString()) {
                        ((StringSetting) settingobj).setStringValue(setting.getAsString());
                    }
                    return settingobj;
                } catch (Exception ignored) {
                    return null;
                }
            } else {
                try {
                    return (Setting) SCSettings.class.getDeclaredField(name).get(null);
                } catch (Exception ignored) {
                    return null;
                }
            }
        } else {
            try {
                return (Setting) SCSettings.class.getDeclaredField(name).get(null);
            } catch (Exception ignored) {
                return null;
            }
        }
    }

}
