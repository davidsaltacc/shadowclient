package net.justacoder.shadowclient.main.config;

import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;
import net.justacoder.shadowclient.main.setting.settings.ColorSetting;
import net.justacoder.shadowclient.main.setting.settings.EnumSetting;
import net.justacoder.shadowclient.main.setting.settings.PaddingSetting;
import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.justacoder.shadowclient.main.ui.clickgui.Frame;
import org.jetbrains.annotations.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShadowClientSettings {

    public static final BooleanSetting VanillaSpoof;
    public static final BooleanSetting ChatMessages;
    public static final BooleanSetting BlurBackground;
    public static final ColorSetting loadingScreenBackgroundColor;
    public static final EnumSetting<ModuleSorting> moduleSorting;
    public static final EnumSetting<ModuleSortingDirection> moduleSortingDirection;

    static {
        VanillaSpoof = addSetting(new BooleanSetting(TranslatableString.of("setting.shadowclient.vanillaspoof"), false));
        ChatMessages = addSetting(new BooleanSetting(TranslatableString.of("setting.shadowclient.chatmessages"), true));
        BlurBackground = addSetting(new BooleanSetting(TranslatableString.of("setting.shadowclient.blurbackground"), true));
        addSetting(new PaddingSetting());
        loadingScreenBackgroundColor = addSetting(new ColorSetting(TranslatableString.of("setting.shadowclient.loading_background_color"), -14997957));
        addSetting(new PaddingSetting());
        moduleSorting = addSetting(new EnumSetting<>(TranslatableString.of("setting.shadowclient.module_sorting"), ModuleSorting.CREATION_ORDER));
        moduleSortingDirection = addSetting(new EnumSetting<>(TranslatableString.of("setting.shadowclient.module_sorting_direction"), ModuleSortingDirection.ASCENDING));

        moduleSorting.addChangeCallback((__, ___) -> Frame.allFrames.forEach(Frame::resortModules));
        moduleSortingDirection.addChangeCallback((__, ___) -> Frame.allFrames.forEach(Frame::resortModules));
    }

    private static Map<String, Setting> allSCSettings;

    public static <S extends Setting> S addSetting(S setting) {
        if (allSCSettings == null) {
            allSCSettings = new LinkedHashMap<>();
        }
        allSCSettings.put(setting.name.getKey(), setting);
        return setting;
    }

    public static @Nullable Setting getSetting(String name) {
        return allSCSettings.getOrDefault(name, null);
    }

    public static Map<String, Setting> getAllSCSettings() {
        return allSCSettings;
    }

    public enum ModuleSorting {
        CREATION_ORDER,
        ALPHABETICAL
    }

    public enum ModuleSortingDirection {
        ASCENDING,
        DESCENDING
    }

}
