package net.shadowclient.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.shadowclient.main.SCMain;

@Environment(EnvType.CLIENT)
public class FabricInitializer implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		SCMain.init();
	}
}