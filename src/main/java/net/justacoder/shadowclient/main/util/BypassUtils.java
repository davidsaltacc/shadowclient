package net.justacoder.shadowclient.main.util;

import net.justacoder.shadowclient.main.SCMain;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public abstract class BypassUtils {

    public static void sendFiveMovementPackets() {
        for (int i = 0; i < 5; i++) {
            SCMain.mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(SCMain.mc.player.getX(), SCMain.mc.player.getY(), SCMain.mc.player.getZ(), true, SCMain.mc.player.horizontalCollision));
        }
    }

}
