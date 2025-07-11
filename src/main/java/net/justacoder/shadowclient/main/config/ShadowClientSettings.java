package net.justacoder.shadowclient.main.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.setting.SettingEnum;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;
import net.justacoder.shadowclient.main.setting.settings.ColorSetting;
import net.justacoder.shadowclient.main.setting.settings.EnumSetting;
import net.justacoder.shadowclient.main.setting.settings.PaddingSetting;
import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.justacoder.shadowclient.main.ui.clickgui.Frame;
import org.jetbrains.annotations.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShadowClientSettings implements ConfigSaveable {

    public static final BooleanSetting VanillaSpoof;
    public static final BooleanSetting ChatMessages;
    public static final BooleanSetting BlurBackground;
    public static final ColorSetting loadingScreenBackgroundColor;
    public static final EnumSetting<ModuleSorting> moduleSorting;
    public static final EnumSetting<ModuleSortingDirection> moduleSortingDirection;
    public static final EnumSetting<NotificationCorner> notificationCorner;
    public static final EnumSetting<PushNotificationCorner> pushNotificationCorner;

    static {
        VanillaSpoof = addSetting(new BooleanSetting(TranslatableString.of("setting.shadowclient.vanillaspoof"), false));
        ChatMessages = addSetting(new BooleanSetting(TranslatableString.of("setting.shadowclient.chatmessages"), true));
        BlurBackground = addSetting(new BooleanSetting(TranslatableString.of("setting.shadowclient.blurbackground"), true));
        addSetting(new PaddingSetting());
        loadingScreenBackgroundColor = addSetting(new ColorSetting(TranslatableString.of("setting.shadowclient.loading_background_color"), -14997957));
        addSetting(new PaddingSetting());
        moduleSorting = addSetting(new EnumSetting<>(TranslatableString.of("setting.shadowclient.module_sorting"), ModuleSorting.CREATION_ORDER));
        moduleSortingDirection = addSetting(new EnumSetting<>(TranslatableString.of("setting.shadowclient.module_sorting_direction"), ModuleSortingDirection.ASCENDING));
        notificationCorner = addSetting(new EnumSetting<>(TranslatableString.of("setting.shadowclient.notification_corner"), NotificationCorner.Top_Left));
        pushNotificationCorner = addSetting(new EnumSetting<>(TranslatableString.of("setting.shadowclient.push_notifis_corner"), PushNotificationCorner.Top_Left));

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

    public static ShadowClientSettings getInstance() {
        return INSTANCE;
    }

    public static final ShadowClientSettings INSTANCE = new ShadowClientSettings();

    @Override
    public JsonObject writeConfig() {
        JsonObject object = new JsonObject();
        allSCSettings.forEach((name, setting) -> object.add(name, setting.writeConfig()));
        return object;
    }

    @Override
    public void readConfig(JsonObject in) {
        allSCSettings.forEach((name, setting) -> {
            try {
                JsonElement element = in.get(name);
                if (element != null) {
                    setting.readConfig(element.getAsJsonObject());
                }
            } catch (Exception e) {
                ShadowClientMain.error("Failed to read config for setting " + name + ": " + e);
            }
        });
    }

    public enum ModuleSorting implements SettingEnum {
        CREATION_ORDER("name.settingenum.shadowclient.module_sorting.creation"),
        ALPHABETICAL("name.settingenum.shadowclient.module_sorting.alphabetically");

        private TranslatableString fullName;
        ModuleSorting(String key) {
            this.fullName = TranslatableString.of(key);
        }

        @Override
        public TranslatableString fullName() {
            return fullName;
        }
    }

    public enum ModuleSortingDirection implements SettingEnum {
        ASCENDING("name.settingenum.shadowclient.module_sorting_direction.ascending"),
        DESCENDING("name.settingenum.shadowclient.module_sorting_direction.descending");

        private TranslatableString fullName;
        ModuleSortingDirection(String key) {
            this.fullName = TranslatableString.of(key);
        }

        @Override
        public TranslatableString fullName() {
            return fullName;
        }
    }

    public enum NotificationCorner implements SettingEnum {
        Top_Left("name.settingenum.shadowclient.corner.top_left"),
        Top_Right("name.settingenum.shadowclient.corner.top_right"),
        Bottom_Left("name.settingenum.shadowclient.corner.bottom_left"),
        Bottom_Right("name.settingenum.shadowclient.corner.bottom_right");

        NotificationCorner(String key) {
            this.name = TranslatableString.of(key);
        }

        private TranslatableString name;

        @Override
        public TranslatableString fullName() {
            return name;
        }
    }

    public enum PushNotificationCorner implements SettingEnum {
        Top_Left("name.settingenum.shadowclient.corner.top_left"),
        Top_Right("name.settingenum.shadowclient.corner.top_right"),
        Bottom_Left("name.settingenum.shadowclient.corner.bottom_left"),
        Bottom_Right("name.settingenum.shadowclient.corner.bottom_right");

        PushNotificationCorner(String key) {
            this.name = TranslatableString.of(key);
        }

        private TranslatableString name;

        @Override
        public TranslatableString fullName() {
            return name;
        }
    }

}
