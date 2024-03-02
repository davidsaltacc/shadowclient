package net.justacoder.shadowclient.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.justacoder.shadowclient.main.SCMain;

@Environment(EnvType.CLIENT)
public class FabricInitializer implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		SCMain.init();
	}
}