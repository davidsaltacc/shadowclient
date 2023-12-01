package net.shadowclient.main.module.modules.movement;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@EventListener({PreTickEvent.class})
@SearchTags({"anti fall damage", "no fall damage", "no fall dmg", "no falling damage", "nofalldamage"})
public class NoFallDamage extends Module {
    public NoFallDamage() {
        super("nofall", "No Fall Damage", "You take no fall damage.", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onEvent(Event event) {

        if (mc.player.isFallFlying()) {
            return;
        }
        if (mc.player.fallDistance <= 2) {
            return;
        }
        if (mc.player.getVelocity().y > -0.5) {
            return;
        }

        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
    }
}
