package net.shadowclient.main.module.modules.player;

import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.DeathEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@EventListener({DeathEvent.class})
@SearchTags({"auto respawn", "autorespawn", "automatically respawn", "revive"})
public class AutoRespawn extends Module {
    public AutoRespawn() {
        super("autorespawn", "Auto Respawn", "Instantly respawn upon death.", ModuleCategory.PLAYER);
    }

    @Override
    public void onEvent(Event event) {

        mc.player.requestRespawn();
        mc.setScreen(null);
    }
}
