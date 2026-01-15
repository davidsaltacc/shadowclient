package net.justacoder.shadowclient.main.config;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.SemanticVersion;
import net.fabricmc.loader.api.VersionParsingException;
import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.util.MiscUtils;
import net.minecraft.SharedConstants;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigManager {

    public static final File SC_CONFIG_DIR = Paths.get(FabricLoader.getInstance().getConfigDir().toString(), "shadowclient").toFile();

    private static final boolean PRETTY_PRINT_CONFIG = false;

    public enum ConfigType {

        UI_DATA("ui"),
        MODULE_SETTINGS("modules"),
        CLIENT_SETTINGS("settings"),
        CLIENT_METADATA("meta");

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

    public static void saveConfig() {

        JsonObject newMeta = new JsonObject();
        newMeta.add("client_version", new JsonPrimitive(SCMain.VERSION));
        newMeta.add("game_version", new JsonPrimitive(SharedConstants.getGameVersion().id()));
        setData(ConfigType.CLIENT_METADATA, "data", newMeta);

        GsonBuilder gsonBuilder = new GsonBuilder();
        if (PRETTY_PRINT_CONFIG) {
            gsonBuilder.setPrettyPrinting();
        }
        Gson gson = gsonBuilder.create();

        for (ConfigType type : ConfigType.values()) {

            JsonObject data = type.getData();
            String fullFilename = type.getFilename() + ".json";

            String jsonData = gson.toJson(data);

            Path path = Paths.get(SC_CONFIG_DIR.getAbsolutePath(), fullFilename);
            byte[] dataBytes = jsonData.getBytes();

            try {
                Files.write(path, dataBytes);
            } catch (IOException e) {
                SCMain.error("Failed to save config to {}: {}", path.toFile().getAbsolutePath(), MiscUtils.stackTraceFromThrowable(e));
            }

        }

    }

    public static void loadConfig() {


        JsonObject metadataData = new JsonObject(); // TODO actually load
        // read the ConfigType.CLIENT_METADATA file to metadataData

        JsonElement metadataElement = metadataData.get("data");
        if (metadataElement == null || !metadataElement.isJsonObject()) {
            SCMain.warn("Found existing configuration, but not containing metadata. Skipping config load");
            return;
        }
        JsonObject metadata = metadataElement.getAsJsonObject();

        try {

            String oldClientVersion = metadata.get("client_version").getAsString();
            String oldGameVersion = metadata.get("game_version").getAsString();
            String currentClientVersion = SCMain.VERSION;
            String currentGameVersion = SharedConstants.getGameVersion().id();

            SemanticVersion oldClientSemVer = SemanticVersion.parse(oldClientVersion);
            SemanticVersion oldGameSemVer = SemanticVersion.parse(oldGameVersion);
            SemanticVersion currentClientSemVer = SemanticVersion.parse(currentClientVersion);
            SemanticVersion currentGameSemVer = SemanticVersion.parse(currentGameVersion);

            // TODO switch from 0.2 to 0.3 - not picked up anyway, config path changed.
            // TODO switch from new client version to older - bigger warn
            // TODO switch anything else - small warn

        } catch (UnsupportedOperationException e) {
            SCMain.warn("Error parsing config metadata file. Skipping config load");
            SCMain.error(MiscUtils.stackTraceFromThrowable(e));
            return;
        } catch (VersionParsingException e) {
            SCMain.warn("Somehow failed to parse client or game versions (this should not happen, unless the config was modified externally)");
            SCMain.error(MiscUtils.stackTraceFromThrowable(e));
            return;
        }

        for (ConfigType type : ConfigType.values()) {

            if (type == ConfigType.CLIENT_METADATA) {
                continue;
            }

            // TODO load type

        }

    }

    // TODO load config in veryEarly init point and save config on exit, and possibly ClickGUI close? (probably just add a shutdown hook like before) (also add asynchronous saving, please)

}
