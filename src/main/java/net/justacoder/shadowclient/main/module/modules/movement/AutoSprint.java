package net.justacoder.shadowclient.main.module.modules.movement;

import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@EventListener({PreTickEvent.class})
public class AutoSprint extends Module {

    public AutoSprint() {
        super("autosprint", ModuleCategory.MOVEMENT, new String[]{"autosprint", "automatically sprint", "auto sprint"});
    }

    @Override
    public void onEvent(Event event) {
        if (mc.options.forwardKey.isPressed()) {
            mc.player.setSprinting(true);
        }
    }
}
