package net.shadowclient.main.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.shadowclient.main.SCMain;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleManager;
import net.shadowclient.main.setting.settings.BooleanSetting;
import net.shadowclient.main.setting.settings.EnumSetting;
import net.shadowclient.main.setting.settings.NumberSetting;
import net.shadowclient.main.setting.settings.StringSetting;
import net.shadowclient.main.util.FileUtils;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ConfigFiles {

    public static File getConfigFile() {
        return FabricLoader.getInstance().getConfigDir().resolve(SCMain.ClientModId + ".config.json").toFile();
    }

    public static void saveConfig() {

        JsonObject json = new JsonObject();
        JsonObject modulescontainer = new JsonObject();
        JsonObject clientdata = new JsonObject();

        Map<String, Module> modules = ModuleManager.getAllModules();
        modules.forEach((name, module) -> {
            JsonObject modulejson = new JsonObject();

            modulejson.addProperty("enabled", module.enabled);

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
                    settings.addProperty(setting.name, value.toString());
                    settings.addProperty(setting.name + "_ENUMPATH", value.getClass().toString());
                }
            });

            modulejson.add("settings", settings);
            modulescontainer.add(name, modulejson);
        });

        clientdata.addProperty("version", SCMain.ClientVersion);

        json.add("client", clientdata);
        json.add("modules", modulescontainer);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String out = gson.toJson(json);

        FileUtils.writeFile(getConfigFile(), out);
    }

    public static void loadConfig() {
        String text = FileUtils.readFile(getConfigFile());
        if (text == null) {
            saveConfig();
            return;
        }
        JsonObject json = (new Gson()).fromJson(text, JsonObject.class);
        JsonObject clientdata = json.getAsJsonObject("client");
        String version = clientdata.get("version").getAsString();
        if (!version.equals(SCMain.ClientVersion)) {
            SCMain.warn("Config version " + version + " does not match current version " + SCMain.ClientVersion);
        }

        json = json.getAsJsonObject("modules");

        Map<String, JsonObject> modules = new HashMap<>();
        JsonObject finalJson = json; // i hate java
        json.keySet().forEach((name) -> modules.put(name, finalJson.get(name).getAsJsonObject()));

        modules.forEach((name, object) -> {
            Module module = ModuleManager.getModule(name);
            JsonObject settings = object.get("settings").getAsJsonObject();

            settings.keySet().forEach((setting) -> {
                JsonElement settingjson = settings.get(setting);
                if (settingjson.isJsonPrimitive() && settingjson.getAsJsonPrimitive().isNumber()) {
                    Number value = settingjson.getAsNumber();
                    module.settings.forEach((settingobj) -> {
                        if (settingobj.name.equals(setting)) {
                            settingobj.setNumberValue(value);
                        }
                    });
                }
                if (settingjson.isJsonPrimitive() && settingjson.getAsJsonPrimitive().isBoolean()) {
                    boolean value = settingjson.getAsBoolean();
                    module.settings.forEach((settingobj) -> {
                        if (settingobj.name.equals(setting)) {
                            settingobj.setBooleanValue(value);
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
                                        ((EnumSetting) settingobj).setEnumValue(enumConstant);
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        } else {
                            module.settings.forEach((settingobj) -> {
                                if (settingobj.name.equals(setting)) {
                                    ((StringSetting) settingobj).setStringValue(value);
                                }
                            });
                        }
                    }
                }
            });
            SCMain.setModuleEnabled(name, object.get("enabled").getAsBoolean(), false);
        });
    }
}
