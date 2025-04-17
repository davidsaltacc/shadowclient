package net.justacoder.shadowclient.main.module.modules.movement;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@EventListener({PreTickEvent.class})
@SearchTags({"anti fall damage", "no fall damage", "no fall dmg", "no falling damage", "nofalldamage"})
public class NoFallDamage extends Module {
    public NoFallDamage() {
        super("nofall", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onEvent(Event event) {

        if (mc.player.isGliding()) {
            return;
        }
        if (mc.player.getVelocity().y > -0.5) {
            return;
        }

        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), true, mc.player.horizontalCollision));

    }
}
