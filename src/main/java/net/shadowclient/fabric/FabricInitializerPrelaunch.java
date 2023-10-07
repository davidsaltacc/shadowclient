package net.shadowclient.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

@Environment(EnvType.CLIENT)
public class FabricInitializerPrelaunch implements PreLaunchEntrypoint {

    @Override
    public void onPreLaunch() {
        // unused meh
    }
}
