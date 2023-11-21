package net.shadowclient.main.module.modules.world;

import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.event.events.VelocityFromFluidEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@EventListener({VelocityFromFluidEvent.class})
@SearchTags({"no water push", "water push", "anti water push", "antiwaterpush"})
public class NoWaterPush extends Module {
    public NoWaterPush() {
        super("nowaterpush", "No Water Push", "Prevents flowing water from pushing you.", ModuleCategory.WORLD);
    }

    @Override
    public void onEvent(Event event) {

        if (((VelocityFromFluidEvent) event).entity == mc.player) {
            event.cancel();
        }
    }
}
