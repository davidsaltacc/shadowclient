package net.justacoder.shadowclient.main.util;

import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.justacoder.shadowclient.main.SCMain;

public abstract class PlayerUtils {
    public static Vec3d getHandOffset(Hand hand, double yaw) {
        Arm arm = SCMain.mc.options.getMainArm().getValue();

        boolean right = arm == Arm.RIGHT && hand == Hand.MAIN_HAND || arm == Arm.LEFT && hand == Hand.OFF_HAND;

        double sideMul = right ? -1 : 1;
        double offX = Math.cos(yaw) * 0.16 * sideMul;
        double offY = SCMain.mc.player.getStandingEyeHeight() - 0.1;
        double offZ = Math.sin(yaw) * 0.16 * sideMul;

        return new Vec3d(offX, offY, offZ);
    }
}
