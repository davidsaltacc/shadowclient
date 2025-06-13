package net.justacoder.shadowclient.main.config;

import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;
import net.justacoder.shadowclient.main.translations.TranslatableString;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ShadowClientSettings { // TODO rewrite whatever this horrendousness is

    public static final BooleanSetting VanillaSpoof = addSetting(new BooleanSetting(new TranslatableString("setting.shadowclient.vanillaspoof"), false));
    public static final BooleanSetting ChatMessages = addSetting(new BooleanSetting(new TranslatableString("setting.shadowclient.chatmessages"), true));
    public static final BooleanSetting BlurBackground = addSetting(new BooleanSetting(new TranslatableString("setting.shadowclient.blurbackground"), true));

    public static final int LOADING_SCREEN_BGND_COLOR = -14997957; // TODO make configurable maybe

    public static final Map<String, Setting> allSCSettings = new HashMap<>();

    public static <S extends Setting> S addSetting(S setting) {
        allSCSettings.put(setting.name.getKey(), setting);
        return setting;
    }

    public static @Nullable Setting getSetting(String name) {
        return allSCSettings.getOrDefault(name, null);
    }

}
