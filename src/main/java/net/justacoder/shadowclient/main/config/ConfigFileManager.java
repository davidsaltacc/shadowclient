package net.justacoder.shadowclient.main.config;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.nio.file.Paths;

public class ConfigFileManager { // i don't know why, but i am for some reason scared of implementing a config system based on what we currently have

    public static final File SC_CONFIG_DIR = Paths.get(FabricLoader.getInstance().getConfigDir().toUri().toString(), "shadowclient").toFile();

    // TODO i suppose first make an in-memory-only store, so stuff like frame positions can get stored at least, after that save and load that in-memory storage on boot, exit, and possibly ClickGUI close? (asynchronous saving, please)

}
