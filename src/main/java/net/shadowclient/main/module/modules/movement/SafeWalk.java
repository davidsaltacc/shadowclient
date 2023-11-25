package net.shadowclient.main.module.modules.movement;

import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.ClipAtLedgeEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.module.ModuleManager;

@EventListener({ClipAtLedgeEvent.class})
@SearchTags({"safe walk", "safewalk", "auto sneak", "autosneak"})
public class SafeWalk extends Module {
    public SafeWalk() {
        super("safewalk", "Safe Walk", "Stops you from falling off ledges when walking.", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onEnable() {
        ModuleManager.StepUpModule.setDisabled(true, false); // incompatible
        super.onEnable();
    }

    @Override
    public void onEvent(Event event) {
        if (!(event instanceof ClipAtLedgeEvent evt)) {
            return;
        }

        if (mc.player.isSneaking()) {
            return;
        }

        evt.setClip();
    }
}
