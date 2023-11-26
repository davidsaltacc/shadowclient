package net.shadowclient.main.module.modules.movement;

import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@EventListener({PreTickEvent.class})
@SearchTags({"airjump", "air jump", "jetpack"})
public class AirJump extends Module {
    public AirJump() {
        super("airjump", "Airjump", "Allows you to jump in the air.", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onEvent(Event event) {

        if (mc.options.jumpKey.isPressed()) {
            mc.player.jump();
        }
    }
}
