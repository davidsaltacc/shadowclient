package net.shadowclient.main.module.modules.movement;

import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@EventListener({PreTickEvent.class})
@SearchTags({"fish", "autoswim", "auto swim", "automatically swim", "easy swim"})
public class AutoSwim extends Module {
    public AutoSwim() {
        super("autoswim", "Autoswim", "Automatically swim when in water.", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onEvent(Event event) {

        if(mc.player.horizontalCollision || mc.player.isSneaking()) {
            return;
        }

        if(!mc.player.isTouchingWater()) {
            return;
        }

        if(mc.player.forwardSpeed > 0) {
            mc.player.setSprinting(true);
        }
    }
}
