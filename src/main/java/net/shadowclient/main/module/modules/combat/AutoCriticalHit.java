package net.shadowclient.main.module.modules.combat;

import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@EventListener({PreTickEvent.class})
@SearchTags({"autocrit", "criticals", "automatical crit", "auto critical hit"})
public class AutoCriticalHit extends Module {

    public AutoCriticalHit() {
        super("autocrit", "Criticals", "Automatically land critical hits every time. ", ModuleCategory.COMBAT);
    }

    @Override
    public void onEvent(Event event) {
        if (!mc.options.attackKey.isPressed()) {
            return;
        }
        if (mc.crosshairTarget == null || mc.crosshairTarget.getType() != HitResult.Type.ENTITY || !(((EntityHitResult) mc.crosshairTarget).getEntity() instanceof LivingEntity)) {
            return;
        }
        if (!mc.player.isOnGround()) {
            return;
        }
        if (mc.player.isTouchingWater() || mc.player.isInLava()) {
            return;
        }

        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() + 0.0625d, mc.player.getZ(), true));
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), false));
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() + 1.1E-5d, mc.player.getZ(), false));
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), false));
    }
}
