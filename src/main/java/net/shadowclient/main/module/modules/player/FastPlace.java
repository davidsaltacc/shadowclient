package net.shadowclient.main.module.modules.player;

import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.mixin.MinecraftClientAccessor;

@EventListener({PreTickEvent.class})
@SearchTags({"fastplace", "fast placing", "fast place"})
public class FastPlace extends Module {
    public FastPlace() {
        super("fastplace", "Fast Place", "Removes the item use cooldown.", ModuleCategory.PLAYER);
    }

    @Override
    public void onEvent(Event event) {
        ((MinecraftClientAccessor) mc).setItemUseCooldown(0);
    }
}
