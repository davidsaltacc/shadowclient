package net.justacoder.shadowclient.main.module.modules.fun;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.random.Random;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@EventListener({PreTickEvent.class})
@SearchTags({"derpy", "head shake", "retarded"})
public class Derpy extends Module {

    private final Random random = Random.create();

    public Derpy() {
        super("derpy", "Derpy", "Rotates your head around like wild (visible to others).", ModuleCategory.FUN);
    }

    @Override
    public void onEvent(Event event) {

        float yaw = mc.player.getYaw() + random.nextFloat() * 360F - 180F;
        float pitch = random.nextFloat() * 180F - 90F;

        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(yaw, pitch, mc.player.isOnGround()));
    }
}
