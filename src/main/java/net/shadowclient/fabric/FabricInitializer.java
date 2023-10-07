package net.shadowclient.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.shadowclient.main.SCMain;
import net.shadowclient.main.event.events.Render3DEvent;

@Environment(EnvType.CLIENT)
public class FabricInitializer implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		SCMain.init();

		WorldRenderEvents.END.register((WorldRenderContext context) -> { // im lazy
			SCMain.OnEvent(new Render3DEvent(context.matrixStack(), context.tickDelta()));
		});
	}
}