package net.shadowclient.main.module.modules.movement;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@SearchTags({"autosprint", "automatically sprint", "auto sprint"})
public class AutoSprint extends Module {

    public AutoSprint() {
        super("autosprint", "Autosprint", ModuleCategory.MOVEMENT);
    }

    @Override
    public void OnEvent(Event event) {
        if (!(event instanceof PreTickEvent)) {
            return;
        }

        if (mc.options.forwardKey.isPressed()) {
            mc.player.setSprinting(true);
        }
    }
}
