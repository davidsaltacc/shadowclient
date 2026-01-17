package net.justacoder.shadowclient.main.config;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.SemanticVersion;
import net.fabricmc.loader.api.Version;
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

        SCMain.info("Saving config");

        if (!SC_CONFIG_DIR.exists()) {
            try {
                Files.createDirectory(SC_CONFIG_DIR.toPath());
            } catch (IOException e) {
                SCMain.info("Error creating config directory, skipping config load.");
                SCMain.error(MiscUtils.stackTraceFromThrowable(e));
            }
        }

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

            try {
                Files.writeString(path, jsonData);
            } catch (IOException e) {
                SCMain.error("Failed to save config to {}.", path.toFile().getAbsolutePath());
                SCMain.error(MiscUtils.stackTraceFromThrowable(e));
            }

        }

    }

    public static void loadConfig() {

        SCMain.info("Loading config");

        Path path = Paths.get(SC_CONFIG_DIR.getAbsolutePath(), ConfigType.CLIENT_METADATA.getFilename() + ".json");
        String metadataString;

        if (!path.toFile().exists()) {
            SCMain.info("No existing config found, skipping config load.");
            return;
        }

        try {
            metadataString = Files.readString(path);
        } catch (IOException e) {
            SCMain.error("Failed to read config from {}, skipping config load.", path.toFile().getAbsolutePath());
            SCMain.error(MiscUtils.stackTraceFromThrowable(e));
            return;
        }

        JsonObject metadataData = (new Gson()).fromJson(metadataString, JsonObject.class);

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

            if (currentClientSemVer.compareTo((Version) oldClientSemVer) < 0) {
                SCMain.warn("WARNING! Switching to older versions of the client may break things. Loading older configs on newer versions is supported, but loading newer config on old versions is not guaranteed to work without issues.");
            }
            if (currentGameSemVer.compareTo((Version) oldGameSemVer) != 0) {
                SCMain.warn("Game version changed since last load. Things may be a bit different or may break. If not done already, please update the client.");
            }

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

            path = Paths.get(SC_CONFIG_DIR.getAbsolutePath(), type.getFilename() + ".json");
            String typeDataString;

            try {
                typeDataString = Files.readString(path);
            } catch (IOException e) {
                SCMain.error("Failed to read config from {}.", path.toFile().getAbsolutePath());
                SCMain.error(MiscUtils.stackTraceFromThrowable(e));
                continue;
            }

            type.setData((new Gson()).fromJson(typeDataString, JsonObject.class));

        }

    }

}
