package net.justacoder.shadowclient.main.module.modules.player;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.justacoder.shadowclient.main.annotations.DoNotSaveState;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@DoNotSaveState
@EventListener({PreTickEvent.class})
public class FastBlockBreak extends Module {
    public FastBlockBreak() {
        super("fastbreak", ModuleCategory.PLAYER, new String[]{"fastblockbreak", "fastbreak", "fast block break", "fast break", "block break", "speed break"});
    }

    @Override
    public void onEvent(Event event) {
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
    public boolean onDisable() {
        if (mc.player != null) {
            removeHaste();
        }
        return super.onDisable();
    }
}
