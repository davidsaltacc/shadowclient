package net.justacoder.shadowclient.fabric;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.justacoder.shadowclient.main.LifecyclePoints;

public class FabricPreLaunch implements PreLaunchEntrypoint {

    @Override
    public void onPreLaunch() {
        LifecyclePoints.preFabricLaunch();
    }

}