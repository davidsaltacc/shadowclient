package net.shadowclient.main.module.modules.movement;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.ClipAtLedgeEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.module.ModuleManager;

@SearchTags({"safe walk", "safewalk", "auto sneak", "autosneak"})
public class SafeWalk extends Module {
    public SafeWalk() {
        super("safewalk", "Safe Walk", "Stops you from falling off ledges when walking.", ModuleCategory.MOVEMENT);
    }

    @Override
    public void OnEnable() {
        ModuleManager.StepUpModule.setDisabled(); // incompatible
    }

    @Override
    public void OnEvent(Event event) {
        if (!(event instanceof ClipAtLedgeEvent evt)) {
            return;
        }

        if (mc.player.isSneaking()) {
            return;
        }

        evt.setClip();
    }
}
