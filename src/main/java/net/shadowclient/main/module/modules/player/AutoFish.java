package net.shadowclient.main.module.modules.player;

import net.minecraft.util.Hand;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@SearchTags({"automatically fish", "autofish", "auto fish", "fish", "auto fish hack"})
public class AutoFish extends Module {
    public AutoFish() {
        super("autofish", "Autofish", ModuleCategory.PLAYER);
    }


    public int recastRodCountdown = -1;

    @Override
    public void OnEvent(Event event) {
        if (!(event instanceof PreTickEvent)) {
            return;
        }

        if (recastRodCountdown > 0) {
            recastRodCountdown--;
        }

        if (recastRodCountdown == 0 && this.enabled) {
            mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
            recastRodCountdown = -1;
        }

    }

    public void setRecastRodCountdown(int countdown) {
        recastRodCountdown = countdown;
    }
}
