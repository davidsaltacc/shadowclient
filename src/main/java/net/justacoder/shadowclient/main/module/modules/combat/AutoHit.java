package net.justacoder.shadowclient.main.module.modules.combat;

import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

@EventListener(PreTickEvent.class)
public class AutoHit extends Module {

    public AutoHit() {
        super("autohit", ModuleCategory.COMBAT, new String[]{"AutoHit", "automatic hit", "automatic damage", "auto entity hit"});
    }

    @Override
    public void onEvent(Event event) {

        if (mc.crosshairTarget == null || mc.crosshairTarget.getType() != HitResult.Type.ENTITY || mc.player == null) {
            return;
        }

        mc.interactionManager.attackEntity(mc.player, ((EntityHitResult) mc.crosshairTarget).getEntity());
        mc.player.swingHand(Hand.MAIN_HAND);

    }
}
