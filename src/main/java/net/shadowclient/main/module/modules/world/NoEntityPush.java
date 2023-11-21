package net.shadowclient.main.module.modules.world;

import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.event.events.VelocityFromEntityEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@EventListener({VelocityFromEntityEvent.class})
@SearchTags({"no entity push", "entity push", "anti entity push", "antientitypush"})
public class NoEntityPush extends Module {
    public NoEntityPush() {
        super("noentitypush", "No Entity Push", "Prevents you from getting pushed by entities.", ModuleCategory.WORLD);
    }

    @Override
    public void onEvent(Event event) {
        if (((VelocityFromEntityEvent) event).entity == mc.player) {
            event.cancel();
        }
    }
}
