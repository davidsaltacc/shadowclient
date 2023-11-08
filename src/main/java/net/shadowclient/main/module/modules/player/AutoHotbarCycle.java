package net.shadowclient.main.module.modules.player;

import net.minecraft.entity.player.PlayerInventory;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@SearchTags({"hotbar cycle", "switch", "autohotbarcycle"})
public class AutoHotbarCycle extends Module {

    public AutoHotbarCycle() {
        super("autohotbarcycle", "Hotbar Cycle", ModuleCategory.PLAYER);
    }

    @Override
    public void OnEvent(Event event) {
        if (!(event instanceof PreTickEvent)) {
            PlayerInventory inv = mc.player.getInventory();

            if (inv.selectedSlot == 8) {
                inv.selectedSlot = 0;
                return;
            }
            inv.selectedSlot++;
        }
    }
}
