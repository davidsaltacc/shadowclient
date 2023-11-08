package net.shadowclient.main.module.modules.player;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.mixininterface.IMinecraftClient;

@SearchTags({"fastplace", "fast placing", "fast place"})
public class FastPlace extends Module {
    public FastPlace() { // TODO test
        super("fastplace", "Fast Place", ModuleCategory.PLAYER);
    }

    @Override
    public void OnEvent(Event event) {
        if (!(event instanceof PreTickEvent)) {
            return;
        }

        ((IMinecraftClient) mc).setItemUseCooldown(0);
    }
}
