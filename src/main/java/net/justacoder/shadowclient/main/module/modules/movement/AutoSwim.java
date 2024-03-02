package net.justacoder.shadowclient.main.module.modules.movement;

import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

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
