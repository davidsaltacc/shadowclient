package net.justacoder.shadowclient.fabric;

import net.fabricmc.api.ModInitializer;
import net.justacoder.shadowclient.main.InitializationPoints;

public class FabricLaunch implements ModInitializer {

    @Override
    public void onInitialize() {
        InitializationPoints.fabricLaunch();
    }

}