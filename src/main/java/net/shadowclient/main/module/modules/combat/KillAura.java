package net.shadowclient.main.module.modules.combat;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.BooleanSetting;
import net.shadowclient.main.util.RotationUtils;
import net.shadowclient.main.util.WorldUtils;

@SearchTags({"killaura", "kill aura", "auto kill", "auto hit"})
public class KillAura extends Module {

    public final BooleanSetting LEGIT = new BooleanSetting("Legit", true);

    public KillAura() {
        super("killaura", "Kill Aura", "Attacks every attackable entity in your range.", ModuleCategory.COMBAT);
        addSetting(LEGIT);
    }

    @Override
    public void OnEvent(Event event) {
        if (!(event instanceof PreTickEvent)) {
            return;
        }

        mc.world.getEntities().forEach(entity -> {
            if (entity == mc.player) {
                return;
            }
            if (entity instanceof LivingEntity) {
                if (mc.player.distanceTo(entity) <= 6.2173613f) {
                    if (entity.isAlive()) {
                        if (LEGIT.booleanValue()) {
                            if (WorldUtils.lineOfSight(mc.player, entity) && !(mc.currentScreen instanceof HandledScreen<?>)) {
                                RotationUtils.rotateToVec3d(entity.getPos());
                                mc.interactionManager.attackEntity(mc.player, entity);
                                mc.player.swingHand(Hand.MAIN_HAND);
                            }
                        } else {
                            mc.interactionManager.attackEntity(mc.player, entity);
                            mc.player.swingHand(Hand.MAIN_HAND);
                        }
                    }
                }
            }
        });
    }
}
