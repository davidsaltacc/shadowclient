package net.justacoder.shadowclient.main.module.modules.player;

import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.mixin.MinecraftClientAccessor;

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
