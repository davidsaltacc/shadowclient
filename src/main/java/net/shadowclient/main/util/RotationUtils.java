package net.shadowclient.main.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotationUtils {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void rotateToVec3d(Vec3d vec3d) {
        float[] needed = RotationUtils.getNeededRotations(vec3d);
        ClientPlayerEntity player = mc.player;

        float currentWrapped = MathHelper.wrapDegrees(player.getYaw());
        float intendedWrapped = MathHelper.wrapDegrees(needed[0]);

        float change = MathHelper.wrapDegrees(intendedWrapped - currentWrapped);

        float yaw = player.getYaw() + change;
        float pitch = needed[1];

        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(yaw, pitch, mc.player.isOnGround()));
    }

    public static float[] getNeededRotations(Vec3d vec) {
        Vec3d eyesPos = new Vec3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ());

        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));

        return new float[]{yaw, pitch};
    }

}
