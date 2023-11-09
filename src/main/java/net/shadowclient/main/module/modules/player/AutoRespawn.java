package net.shadowclient.main.module.modules.player;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.DeathEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@SearchTags({"auto respawn", "autorespawn", "automatically respawn", "revive"})
public class AutoRespawn extends Module {
    public AutoRespawn() {
        super("autorespawn", "Auto Respawn", "Instantly respawn upon death.", ModuleCategory.PLAYER);
    }

    @Override
    public void OnEvent(Event event) {
        if (!(event instanceof DeathEvent)) {
            return;
        }

        mc.player.requestRespawn();
        mc.setScreen(null);
    }
}
