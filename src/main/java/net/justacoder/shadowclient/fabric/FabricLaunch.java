package net.justacoder.shadowclient.fabric;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FabricLaunch implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("shadowclient");
    public static final String VERSION = /*$ mod_version*/ "0.3";
    public static final String MINECRAFT = /*$ minecraft*/ "1.21.11";

    @Override
    public void onInitialize() {

    }

}