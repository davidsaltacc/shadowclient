package net.shadowclient.main.module.modules.movement;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@SearchTags({"anti fall damage", "no fall damage", "no fall dmg", "no falling damage"})
public class NoFallDamage extends Module {
    public NoFallDamage() {
        super("nofall", "No Fall Damage", "You take no fall damage.", ModuleCategory.MOVEMENT);
    }

    @Override
    public void OnEvent(Event event) {
        if (!(event instanceof PreTickEvent)) {
            return;
        }
        if (mc.player.fallDistance <= (mc.player.isFallFlying() ? 1 : 2)) {
            return;
        }
        if (mc.player.isFallFlying() && mc.player.isSneaking() && !(mc.player.getVelocity().y < -0.5)) {
            return;
        }

        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
    }
}
