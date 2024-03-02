package net.justacoder.shadowclient.main.module.modules.movement;

import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@EventListener({PreTickEvent.class})
@SearchTags({"autosprint", "automatically sprint", "auto sprint"})
public class AutoSprint extends Module {

    public AutoSprint() {
        super("autosprint", "Autosprint", "Automatically sprint instead of walking.", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onEvent(Event event) {
        if (mc.options.forwardKey.isPressed()) {
            mc.player.setSprinting(true);
        }
    }
}
