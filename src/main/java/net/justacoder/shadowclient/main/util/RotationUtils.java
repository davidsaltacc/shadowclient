package net.justacoder.shadowclient.main.util;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;

public abstract class RotationUtils {

    public static void rotatePlayerToVec3d(Vec3d vec3d) {
        ClientPlayerEntity player = ShadowClientMain.mc.player;
        float[] needed = RotationUtils.rotationTowardsF(player.getEyePos(), vec3d);

        float currentWrapped = MathHelper.wrapDegrees(player.getYaw());
        float intendedWrapped = MathHelper.wrapDegrees(needed[0]);

        float change = MathHelper.wrapDegrees(intendedWrapped - currentWrapped);

        float yaw = player.getYaw() + change;
        float pitch = needed[1];

        ShadowClientMain.mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(yaw, pitch, ShadowClientMain.mc.player.isOnGround(), ShadowClientMain.mc.player.horizontalCollision));
    }

    public static Quaternionf rotationTowardsQ(Vec3d start, Vec3d towards) {
        float[] rot = rotationTowardsF(start, towards);
        return new Quaternionf().rotationYXZ((float) Math.toRadians(rot[0]), (float) Math.toRadians(rot[1]), 0f);
    }

    public static Quaternionf rotationAwayQ(Vec3d start, Vec3d towards) {
        float[] rot = rotationAwayF(start, towards);
        return new Quaternionf().rotationYXZ((float) Math.toRadians(rot[0]), (float) Math.toRadians(rot[1]), 0f);
    }

    public static Vec3d rotationTowardsV(Vec3d start, Vec3d towards) {
        float[] rot = rotationTowardsF(start, towards);
        return Vec3d.fromPolar(rot[0], rot[1]);
    }

    public static Vec3d rotationAwayV(Vec3d start, Vec3d towards) {
        float[] rot = rotationAwayF(start, towards);
        return Vec3d.fromPolar(rot[0], rot[1]);
    }

    public static float[] rotationTowardsF(Vec3d start, Vec3d end) {
        Vec3d direction = end.subtract(start);
        if (direction.lengthSquared() == 0) {
            return new float[] {0f, 0f};
        }
        direction = direction.normalize();
        float yaw = (float) Math.toDegrees(Math.atan2(direction.z, direction.x)) - 90f;
        float pitch = (float) -Math.toDegrees(Math.asin(direction.y));
        return new float[] {yaw, pitch};
    }

    public static float[] rotationAwayF(Vec3d start, Vec3d end) {
        Vec3d direction = end.subtract(start).negate();
        if (direction.lengthSquared() == 0) {
            return new float[] {0f, 0f};
        }
        direction = direction.normalize();
        float yaw = (float) Math.toDegrees(Math.atan2(direction.z, direction.x)) - 90f;
        float pitch = (float) -Math.toDegrees(Math.asin(direction.y));
        return new float[] {yaw, pitch};
    }

}
