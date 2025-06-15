package net.justacoder.shadowclient.main.config;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.annotations.DoNotSaveState;
import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.setting.settings.*;
import net.justacoder.shadowclient.main.translations.Language;
import net.justacoder.shadowclient.main.ui.clickgui.Frame;
import net.justacoder.shadowclient.main.util.FileUtils;
import net.justacoder.shadowclient.main.util.JavaUtils;
import org.jetbrains.annotations.Nullable;
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Config {

    public static boolean configLoaded;
    public static boolean resetUi = false;

    public static File getConfigFile() {
        return FabricLoader.getInstance().getConfigDir().resolve(ShadowClientMain.CLIENT_MOD_ID + ".config.json").toFile();
    }

    public static void saveConfig() {

        if (!ShadowClientMain.mayWriteConfig) {
            return;
        }

        ShadowClientMain.info("Saving config");

        JsonObject json = new JsonObject();
        JsonObject modulescontainer = new JsonObject();
        JsonObject clientdata = new JsonObject();
        JsonObject scsettings = new JsonObject();
        JsonObject uisettings = new JsonObject();

        Map<String, Module> modules = ModuleManager.getAllModules();
        modules.forEach((name, module) -> {

                JsonObject modulejson = new JsonObject();

                if (!module.getClass().isAnnotationPresent(DoNotSaveState.class)) {
                    modulejson.addProperty("enabled", module.enabled);
                }

                JsonObject settings = new JsonObject();

                module.settings.forEach(setting -> {
                    if (setting instanceof BooleanSetting set) {
                        settings.addProperty(setting.name.getKey(), set.booleanValue());
                    }
                    if (setting instanceof NumberSetting set) {
                        settings.addProperty(setting.name.getKey(), set.numberValue());
                    }
                    if (setting instanceof StringSetting set) {
                        settings.addProperty(setting.name.getKey(), set.stringValue());
                    }
                    if (setting instanceof ColorSetting set) {
                        settings.addProperty(setting.name.getKey(), set.colorValue());
                    }
                    if (setting instanceof EnumSetting<?> set) {
                        Enum<?> value = set.getEnumValue();
                        settings.addProperty(setting.name.getKey(), value.name());
                        settings.addProperty(setting.name.getKey() + "_ENUMPATH", value.getClass().toString());
                    }
                });

                modulejson.add("settings", settings);
                modulescontainer.add(name, modulejson);


        });

        clientdata.addProperty("version", ShadowClientMain.CLIENT_VERSION);

        for (Setting setting : ShadowClientSettings.getAllSCSettings().values()) {
            if (setting instanceof BooleanSetting set) {
                scsettings.addProperty(set.name.getKey(), set.booleanValue());
            }
            if (setting instanceof NumberSetting set) {
                scsettings.addProperty(set.name.getKey(), set.numberValue());
            }
            if (setting instanceof StringSetting set) {
                scsettings.addProperty(set.name.getKey(), set.stringValue());
            }
        }

        JsonObject uiframes = new JsonObject();
        JsonObject mainuiframe = new JsonObject();
        JsonObject settingsframe = new JsonObject();

        List<Frame> mainuiframes = new ArrayList<>(ShadowClientMain.clickGui.frames);
        mainuiframes.add(ShadowClientMain.clickGui.searchFrame);
        mainuiframes.forEach(frame -> {
            JsonObject frameobj = new JsonObject();
            frameobj.addProperty("offset_x", frame.x);
            frameobj.addProperty("offset_y", frame.y);
            frameobj.addProperty("extended", frame.extended);
            mainuiframe.add(frame.category.id, frameobj);
        });

        List<Frame> settingsframes = new ArrayList<>(ShadowClientMain.settingsGui.frames);
        settingsframes.add(ShadowClientMain.settingsGui.searchFrame);
        settingsframes.forEach(frame -> {
            JsonObject frameobj = new JsonObject();
            frameobj.addProperty("offset_x", frame.x);
            frameobj.addProperty("offset_y", frame.y);
            frameobj.addProperty("extended", frame.extended);
            settingsframe.add(frame.category.id, frameobj);
        });

        uiframes.add("main", mainuiframe);
        uiframes.add("settings", settingsframe);
        uisettings.add("frames", uiframes);
        json.add("client", clientdata);
        json.add("settings", scsettings);
        json.add("modules", modulescontainer);
        json.add("ui", uisettings);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String out = gson.toJson(json);

        FileUtils.writeFile(getConfigFile(), out);
    }

    @SuppressWarnings("unchecked")
    public static void loadConfig() {
        String text = FileUtils.readFile(getConfigFile());
        if (text == null) {
            ShadowClientMain.info("Failed to find config file, creating new one.");
            saveConfig();
            return;
        }

        Language englishUs = new Language("en_us");

        JsonObject json = (new Gson()).fromJson(text, JsonObject.class);
        JsonObject clientdata = json.getAsJsonObject("client");
        JsonObject scsettings = json.getAsJsonObject("settings");
        JsonObject uisettings = json.getAsJsonObject("ui");
        String version = clientdata.get("version").getAsString();
        if (!version.equals(ShadowClientMain.CLIENT_VERSION)) {
            ShadowClientMain.warn("Config version " + version + " does not match current version " + ShadowClientMain.CLIENT_VERSION);
            if (ShadowClientMain.CLIENT_VERSION.equals("0.3.0")) {
                ShadowClientMain.warn("Upgrade to 0.3.0 detected, resetting config to avoid issues.");
                saveConfig();
                return;
            }
        }

        json = json.getAsJsonObject("modules");

        Map<String, JsonObject> modules = new ConcurrentHashMap<>();
        JsonObject finalJson = json;
        json.keySet().forEach(name -> modules.put(name, finalJson.get(name).getAsJsonObject()));

        if (scsettings != null) {
            scsettings.keySet().forEach(setting -> {
                JsonPrimitive value = scsettings.getAsJsonPrimitive(setting);
                Setting settingobj = ShadowClientSettings.getSetting(setting);
                if (settingobj != null) {
                    if (value.isBoolean()) {
                        ((BooleanSetting) settingobj).setBooleanValue(value.getAsBoolean());
                    }
                    if (value.isNumber()) {
                        ((NumberSetting) settingobj).setNumberValue(value.getAsNumber());
                    }
                    if (value.isString()) {
                        ((StringSetting) settingobj).setStringValue(value.getAsString());
                    }
                }
            });
        }

        modules.forEach((name, object) -> {
            Module module = ModuleManager.getModule(name);
            JsonObject settings = object.get("settings").getAsJsonObject();

            if (module != null) {

                settings.keySet().forEach(setting -> {
                    JsonElement settingjson = settings.get(setting);
                    if (settingjson.isJsonPrimitive() && settingjson.getAsJsonPrimitive().isNumber()) {
                        Number value = settingjson.getAsNumber();
                        module.settings.forEach(settingobj -> {
                            if (settingobj.name.getKey().equals(setting) || englishUs.getTranslationFor(settingobj.name.getKey()).equals(setting)) {
                                settingobj.shouldCallCallbacks(false);
                                if (settingobj instanceof NumberSetting set) {
                                    set.setNumberValue(value.doubleValue());
                                } else if (settingobj instanceof ColorSetting set) {
                                    set.setColorValue(value.intValue());
                                }
                                settingobj.shouldCallCallbacks(true);
                            }
                        });
                    }
                    if (settingjson.isJsonPrimitive() && settingjson.getAsJsonPrimitive().isBoolean()) {
                        boolean value = settingjson.getAsBoolean();
                        module.settings.forEach(settingobj -> {
                            if (settingobj.name.getKey().equals(setting) || englishUs.getTranslationFor(settingobj.name.getKey()).equals(setting)) {
                                settingobj.shouldCallCallbacks(false);
                                ((BooleanSetting) settingobj).setBooleanValue(value);
                                settingobj.shouldCallCallbacks(true);
                            }
                        });
                    }
                    if (settingjson.isJsonPrimitive() && settingjson.getAsJsonPrimitive().isString()) {
                        String value = settingjson.getAsString();
                        if (!setting.endsWith("_ENUMPATH")) {
                            if (settings.keySet().contains(setting + "_ENUMPATH")) {
                                String enumpath = settings.get(setting + "_ENUMPATH").getAsString().replace("class ", "");
                                String enumvalue = settings.get(setting).getAsString();
                                module.settings.forEach(settingobj -> {
                                    if (settingobj.name.getKey().equals(setting) || englishUs.getTranslationFor(settingobj.name.getKey()).equals(setting)) {
                                        try {
                                            Class<?> enumClass = Class.forName(enumpath);
                                            Enum<?> enumConstant = Enum.valueOf((Class<Enum>) enumClass, enumvalue);
                                            settingobj.shouldCallCallbacks(false);
                                            ((EnumSetting) settingobj).setEnumValue(enumConstant);
                                            settingobj.shouldCallCallbacks(true);
                                        } catch (Exception e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                });
                            } else {
                                module.settings.forEach(settingobj -> {
                                    if ((settingobj.name.getKey().equals(setting) || englishUs.getTranslationFor(settingobj.name.getKey()).equals(setting)) && settingobj instanceof StringSetting) {
                                        settingobj.shouldCallCallbacks(false);
                                        ((StringSetting) settingobj).setStringValue(value);
                                        settingobj.shouldCallCallbacks(true);
                                    }
                                });
                            }
                        }
                    }
                });

                try {
                    if (!module.getClass().isAnnotationPresent(OneClick.class) && !module.getClass().isAnnotationPresent(DoNotSaveState.class)) {
                        ShadowClientMain.setModuleEnabled(name, object.get("enabled").getAsBoolean(), true, false);
                    }
                } catch (Exception e) {
                    ShadowClientMain.error(JavaUtils.stackTraceFromThrowable(e));
                }
            }
        });

        if (!version.equals(ShadowClientMain.CLIENT_VERSION)) {
            resetUi = true;
        }
        if (uisettings != null) {
            JsonObject uiframes = uisettings.getAsJsonObject("frames");
            JsonObject mainuiframe = uiframes.getAsJsonObject("main");
            JsonObject settingsframe = uiframes.getAsJsonObject("settings");

            List<Frame> mainuiframes = new ArrayList<>(ShadowClientMain.clickGui.frames);
            mainuiframes.add(ShadowClientMain.clickGui.searchFrame);
            mainuiframes.forEach(frame -> {
                if (mainuiframe.has(frame.category.id)) {
                    JsonObject frameobj = mainuiframe.getAsJsonObject(frame.category.id);
                    frame.extended = frameobj.get("extended").getAsBoolean();
                    frame.x = frameobj.get("offset_x").getAsInt();
                    frame.y = frameobj.get("offset_y").getAsInt();
                }
            });

            List<Frame> settingsframes = new ArrayList<>(ShadowClientMain.settingsGui.frames);
            settingsframes.add(ShadowClientMain.settingsGui.searchFrame);
            settingsframes.forEach(frame -> {
                if (settingsframe.has(frame.category.id)) {
                    JsonObject frameobj = settingsframe.getAsJsonObject(frame.category.id);
                    frame.extended = frameobj.get("extended").getAsBoolean();
                    frame.x = frameobj.get("offset_x").getAsInt();
                    frame.y = frameobj.get("offset_y").getAsInt();
                }
            });
        }

        configLoaded = true;
    }

    public static void resetConfig() {
        if (getConfigFile().delete()) {
            ShadowClientMain.configDeleted = true;
        } else {
            ShadowClientMain.error("Failed to delete config file.");
        }
    }

}
