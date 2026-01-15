package net.justacoder.shadowclient.main.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Paths;

public class ConfigManager {

    public static final File SC_CONFIG_DIR = Paths.get(FabricLoader.getInstance().getConfigDir().toString(), "shadowclient").toFile();

    public enum ConfigType {

        UI_DATA("ui"),
        MODULE_SETTINGS("modules"),
        CLIENT_SETTINGS("settings");

        private final String filename;
        private JsonObject data;

        ConfigType(String filename) {
            this.filename = filename;
            this.data = new JsonObject();
        }

        private JsonObject getData() {
            return data;
        }

        private void setData(JsonObject data) {
            this.data = data;
        }

        public String getFilename() {
            return filename;
        }

    }

    private static void setConfigJsonObject(ConfigType type, JsonObject data) {
        type.setData(data);
    }

    public static void setData(ConfigType type, String id, JsonObject data) {
        type.getData().add(id, data);
    }

    public static @Nullable JsonObject getData(ConfigType type, String id) {
        JsonElement element = type.getData().get(id);
        return element == null ? null : element.getAsJsonObject();
    }

    // TODO save and load this in-memory storage on boot, exit, and possibly ClickGUI close? (asynchronous saving, please)
    // TODO load config in veryEarly init point

}
