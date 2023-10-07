package net.shadowclient.main.module.modules.movement;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.VelocityFromFluidEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@SearchTags({"no water push", "water push", "anti water push", "antiwaterpush"})
public class NoWaterPush extends Module {
    public NoWaterPush() {
        super("nowaterpush", "No Water Push", ModuleCategory.MOVEMENT);
    }

    @Override
    public void OnEvent(Event event) {
        if (!(event instanceof VelocityFromFluidEvent)) {
            return;
        }

        if (((VelocityFromFluidEvent) event).entity == mc.player) {
            event.cancel();
        }
    }
}
