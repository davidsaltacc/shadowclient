package net.shadowclient.main.module.modules.player;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@SearchTags({"fastblockbreak", "fastbreak", "fast block break", "fast break", "block break", "speed break"})
public class FastBlockBreak extends Module {
    public FastBlockBreak() {
        super("fastbreak", "Fast Break", ModuleCategory.PLAYER);
    }

    @Override
    public void OnEvent(Event event) {
        if (!(event instanceof PreTickEvent)) {
            return;
        }

        StatusEffectInstance haste = mc.player.getStatusEffect(StatusEffects.HASTE);

        if (haste == null || haste.getAmplifier() <= 1) {
            mc.player.setStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, -1, 1, false, false, false), null);
        }
    }

    public void removeHaste() {

        StatusEffectInstance haste = mc.player.getStatusEffect(StatusEffects.HASTE);
        if (haste != null && !haste.shouldShowIcon()) {
            mc.player.removeStatusEffect(StatusEffects.HASTE);
        }
    }

    @Override
    public void OnDisable() {
        removeHaste();
        super.OnDisable();
    }
}
