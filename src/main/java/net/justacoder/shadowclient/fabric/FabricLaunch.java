package net.justacoder.shadowclient.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.justacoder.shadowclient.main.LifecyclePoints;

public class FabricLaunch implements ModInitializer, PreLaunchEntrypoint {

    @Override
    public void onInitialize() {
        LifecyclePoints.fabricLaunch();
    }

    @Override
    public void onPreLaunch() {
        LifecyclePoints.preFabricLaunch();
    }

}