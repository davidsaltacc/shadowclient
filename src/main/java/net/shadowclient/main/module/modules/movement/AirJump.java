package net.shadowclient.main.module.modules.movement;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@SearchTags({"airjump", "air jump", "jetpack"})
public class AirJump extends Module {
    public AirJump() {
        super("airjump", "Airjump", "Allows you to jump in the air.", ModuleCategory.MOVEMENT);
    }

    @Override
    public void OnEvent(Event event) {
        if (!(event instanceof PreTickEvent)) {
            return;
        }

        if (mc.options.jumpKey.isPressed()) {
            mc.player.jump();
        }
    }
}
