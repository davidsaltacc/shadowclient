package net.justacoder.shadowclient.fabric;

import net.fabricmc.api.ModInitializer;
import net.justacoder.shadowclient.main.LifecyclePoints;

public class FabricLaunch implements ModInitializer {

    @Override
    public void onInitialize() {
        LifecyclePoints.fabricLaunch();
    }

}