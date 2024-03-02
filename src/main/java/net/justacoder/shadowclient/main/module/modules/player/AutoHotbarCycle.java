package net.justacoder.shadowclient.main.module.modules.player;

import net.minecraft.entity.player.PlayerInventory;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@EventListener({PreTickEvent.class})
@SearchTags({"hotbar cycle", "switch", "autohotbarcycle"})
public class AutoHotbarCycle extends Module {

    public AutoHotbarCycle() {
        super("autohotbarcycle", "Hotbar Cycle", "Cycles through your hotbar extremely fast.", ModuleCategory.PLAYER);
    }

    @Override
    public void onEvent(Event event) {
        PlayerInventory inv = mc.player.getInventory();

        if (inv.selectedSlot == 8) {
            inv.selectedSlot = 0;
            return;
        }
        inv.selectedSlot++;
    }
}
