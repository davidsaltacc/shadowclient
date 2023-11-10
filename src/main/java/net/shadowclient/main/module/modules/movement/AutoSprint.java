package net.shadowclient.main.module.modules.movement;

import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@EventListener({PreTickEvent.class})
@SearchTags({"autosprint", "automatically sprint", "auto sprint"})
public class AutoSprint extends Module {

    public AutoSprint() {
        super("autosprint", "Autosprint", "Automatically sprint instead of walking.", ModuleCategory.MOVEMENT);
    }

    @Override
    public void OnEvent(Event event) {
        if (mc.options.forwardKey.isPressed()) {
            mc.player.setSprinting(true);
        }
    }
}
