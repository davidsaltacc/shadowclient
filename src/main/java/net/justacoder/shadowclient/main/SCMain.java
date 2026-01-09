package net.justacoder.shadowclient.main;

import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SCMain {

    public static final String VERSION = /*$ mod_version*/ "0.3";
    public static final String MINECRAFT = /*$ minecraft*/ "1.21.11";
    public static final String NAME = /*$ mod_name*/ "ShadowClient";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void init() {
        ExampleKeyEventListener.init();
        info("Initializing {} version {} for Minecraft {}", NAME, VERSION, MINECRAFT);
    }

    public static void info(String string, Object... objects) {
        LOGGER.info(string, objects);
    }

    public static void warn(String string, Object... objects) {
        LOGGER.warn(string, objects);
    }

    public static void error(String string, Object... objects) {
        LOGGER.error(string, objects);
    }

}
