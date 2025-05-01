package net.justacoder.shadowclient.main.util;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public abstract class BypassUtils {

    public static void sendFiveMovementPackets() {
        for (int i = 0; i < 5; i++) {
            ShadowClientMain.mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(ShadowClientMain.mc.player.getX(), ShadowClientMain.mc.player.getY(), ShadowClientMain.mc.player.getZ(), true, ShadowClientMain.mc.player.horizontalCollision));
        }
    }

}
