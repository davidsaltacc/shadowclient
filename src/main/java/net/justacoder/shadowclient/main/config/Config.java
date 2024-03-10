package net.justacoder.shadowclient.main.config;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.annotations.DontSaveState;
import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;
import net.justacoder.shadowclient.main.setting.settings.EnumSetting;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.setting.settings.StringSetting;
import net.justacoder.shadowclient.main.ui.clickgui.Frame;
import net.justacoder.shadowclient.main.util.FileUtils;
import net.justacoder.shadowclient.main.util.JavaUtils;
import org.jetbrains.annotations.Nullable;
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Config {

    public static File getConfigFile() {
        return FabricLoader.getInstance().getConfigDir().resolve(SCMain.ClientModId + ".config.json").toFile();
    }

    public static void saveConfig() {

        SCMain.info("Saving config");

        JsonObject json = new JsonObject();
        JsonObject modulescontainer = new JsonObject();
        JsonObject clientdata = new JsonObject();
        JsonObject scsettings = new JsonObject();
        JsonObject uisettings = new JsonObject();

        Map<String, Module> modules = ModuleManager.getAllModules();
        modules.forEach((name, module) -> {


                JsonObject modulejson = new JsonObject();

                if (!module.getClass().isAnnotationPresent(DontSaveState.class)) {
                    modulejson.addProperty("enabled", module.enabled);
                }

                if (module.moduleButton != null) {
                    modulejson.addProperty("extended", module.moduleButton.extended);
                } else {
                    modulejson.addProperty("extended", false);
                }

                JsonObject settings = new JsonObject();

                module.settings.forEach(setting -> {
                    if (setting instanceof BooleanSetting) {
                        settings.addProperty(setting.name, setting.booleanValue());
                    }
                    if (setting instanceof NumberSetting) {
                        settings.addProperty(setting.name, setting.numberValue());
                    }
                    if (setting instanceof StringSetting) {
                        settings.addProperty(setting.name, ((StringSetting) setting).stringValue());
                    }
                    if (setting instanceof EnumSetting<?>) {
                        Enum<?> value = ((EnumSetting<?>) setting).getEnumValue();
                        settings.addProperty(setting.name, value.name());
                        settings.addProperty(setting.name + "_ENUMPATH", value.getClass().toString());
                    }
                });

                modulejson.add("settings", settings);
                modulescontainer.add(name, modulejson);


        });

        clientdata.addProperty("version", SCMain.ClientVersion);

        Arrays.stream(SCSettings.class.getDeclaredFields()).forEach((field) -> {
            try {
                Setting setting = (Setting) field.get(null);
                if (setting instanceof BooleanSetting) {
                    scsettings.addProperty(field.getName(), setting.booleanValue());
                }
                if (setting instanceof NumberSetting) {
                    scsettings.addProperty(field.getName(), setting.numberValue());
                }
                if (setting instanceof StringSetting) {
                    scsettings.addProperty(field.getName(), ((StringSetting) setting).stringValue());
                }
            } catch (Exception ignored) {}
        });

        JsonObject uiframes = new JsonObject();
        JsonObject mainuiframe = new JsonObject();
        JsonObject settingsframe = new JsonObject();

        List<Frame> mainuiframes = new ArrayList<>(SCMain.clickGui.frames);
        mainuiframes.add(SCMain.clickGui.searchFrame);
        mainuiframes.forEach((frame) -> {
            JsonObject frameobj = new JsonObject();
            frameobj.addProperty("offset_x", frame.x);
            frameobj.addProperty("offset_y", frame.y);
            frameobj.addProperty("extended", frame.extended);
            mainuiframe.add(frame.name, frameobj);
        });

        List<Frame> settingsframes = new ArrayList<>(SCMain.settingsGui.frames);
        settingsframes.add(SCMain.settingsGui.searchFrame);
        settingsframes.forEach((frame) -> {
            JsonObject frameobj = new JsonObject();
            frameobj.addProperty("offset_x", frame.x);
            frameobj.addProperty("offset_y", frame.y);
            frameobj.addProperty("extended", frame.extended);
            settingsframe.add(frame.name, frameobj);
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
        String text;
        try {
            text = FileUtils.readFile(getConfigFile());
        } catch (Exception ignored) {
            saveConfig();
            return;
        }

        // config breaks when changing from 0.2.0 to 0.3.0
        text = text.replace("net.shadowclient", "net.justacoder.shadowclient");
        text = text.replace("WelcomeMsg", "ChatMessages");

        JsonObject json = (new Gson()).fromJson(text, JsonObject.class);
        JsonObject clientdata = json.getAsJsonObject("client");
        JsonObject scsettings = json.getAsJsonObject("settings");
        JsonObject uisettings = json.getAsJsonObject("ui");
        String version = clientdata.get("version").getAsString();
        if (!version.equals(SCMain.ClientVersion)) {
            SCMain.warn("Config version " + version + " does not match current version " + SCMain.ClientVersion);
        }

        json = json.getAsJsonObject("modules");

        Map<String, JsonObject> modules = new ConcurrentHashMap<>();
        JsonObject finalJson = json;
        json.keySet().forEach((name) -> modules.put(name, finalJson.get(name).getAsJsonObject()));

        if (scsettings != null) {
            scsettings.keySet().forEach((setting) -> {
                JsonPrimitive value = scsettings.getAsJsonPrimitive(setting);
                Setting settingobj = SCSettings.getSetting(setting);
                if (settingobj != null) {
                    if (value.isBoolean()) {
                        settingobj.setBooleanValue(value.getAsBoolean());
                    }
                    if (value.isNumber()) {
                        settingobj.setNumberValue(value.getAsNumber());
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

            settings.keySet().forEach((setting) -> {
                JsonElement settingjson = settings.get(setting);
                if (settingjson.isJsonPrimitive() && settingjson.getAsJsonPrimitive().isNumber()) {
                    Number value = settingjson.getAsNumber();
                    module.settings.forEach((settingobj) -> {
                        if (settingobj.name.equals(setting)) {
                            settingobj.shouldCallCallbacks(false);
                            settingobj.setNumberValue(value);
                            settingobj.shouldCallCallbacks(true);
                        }
                    });
                }
                if (settingjson.isJsonPrimitive() && settingjson.getAsJsonPrimitive().isBoolean()) {
                    boolean value = settingjson.getAsBoolean();
                    module.settings.forEach((settingobj) -> {
                        if (settingobj.name.equals(setting)) {
                            settingobj.shouldCallCallbacks(false);
                            settingobj.setBooleanValue(value);
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
                            module.settings.forEach((settingobj) -> {
                                if (settingobj.name.equals(setting)) {
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
                            module.settings.forEach((settingobj) -> {
                                if (settingobj.name.equals(setting) && settingobj instanceof StringSetting) {
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
                if (module != null) {
                    if (!module.getClass().isAnnotationPresent(OneClick.class) && !module.getClass().isAnnotationPresent(DontSaveState.class)) {
                        SCMain.setModuleEnabled(name, object.get("enabled").getAsBoolean(), true, false);
                    }
                    if (module.moduleButton != null) {
                        if (object.get("extended").getAsBoolean()) {
                            module.moduleButton.extended = true;
                            module.moduleButton.parent.updateButtons();
                        }
                    }
                }
            } catch (Exception e) {
                SCMain.error(JavaUtils.stackTraceFromThrowable(e));
            }
        });

        if (uisettings != null) {
            JsonObject uiframes = uisettings.getAsJsonObject("frames");
            JsonObject mainuiframe = uiframes.getAsJsonObject("main");
            JsonObject settingsframe = uiframes.getAsJsonObject("settings");

            List<Frame> mainuiframes = new ArrayList<>(SCMain.clickGui.frames);
            mainuiframes.add(SCMain.clickGui.searchFrame);
            mainuiframes.forEach((frame) -> {
                if (mainuiframe.has(frame.name)) {
                    JsonObject frameobj = mainuiframe.getAsJsonObject(frame.name);
                    frame.extended = frameobj.get("extended").getAsBoolean();
                    frame.x = frameobj.get("offset_x").getAsInt();
                    frame.y = frameobj.get("offset_y").getAsInt();
                }
            });

            List<Frame> settingsframes = new ArrayList<>(SCMain.settingsGui.frames);
            settingsframes.add(SCMain.settingsGui.searchFrame);
            settingsframes.forEach((frame) -> {
                if (settingsframe.has(frame.name)) {
                    JsonObject frameobj = settingsframe.getAsJsonObject(frame.name);
                    frame.extended = frameobj.get("extended").getAsBoolean();
                    frame.x = frameobj.get("offset_x").getAsInt();
                    frame.y = frameobj.get("offset_y").getAsInt();
                }
            });
        }
    }

    public static void resetConfig() {
        if (getConfigFile().delete()) {
            SCMain.configDeleted = true;
        } else {
            SCMain.error("Failed to delete config file.");
        }
    }

    public static @Nullable JsonObject getSCSettings() {
        String text = FileUtils.readFile(getConfigFile());
        if (text == null) {
            saveConfig();
            return null;
        }
        JsonObject json = (new Gson()).fromJson(text, JsonObject.class);
        return json.getAsJsonObject("settings");
    }
}
