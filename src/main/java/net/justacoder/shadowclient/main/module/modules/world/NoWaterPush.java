package net.justacoder.shadowclient.main.module.modules.world;

import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.VelocityFromFluidEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@EventListener({VelocityFromFluidEvent.class})
public class NoWaterPush extends Module {
    public NoWaterPush() {
        super("nowaterpush", ModuleCategory.WORLD, new String[]{"no water push", "water push", "anti water push", "antiwaterpush"});
    }

    @Override
    public void onEvent(Event event) {

        if (((VelocityFromFluidEvent) event).entity == mc.player) {
            event.cancel();
        }
    }
}
