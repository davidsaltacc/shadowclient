package net.justacoder.shadowclient.main.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.justacoder.shadowclient.main.SCMain;

public class WorldUtils {
    public static boolean lineOfSight(Vec3d from, Vec3d to) {
        return raycast(from, to).getType() == HitResult.Type.MISS;
    }
    public static boolean lineOfSight(Entity from, Vec3d to) {
        return lineOfSight(from.getEyePos(), to);
    }
    public static boolean lineOfSight(Vec3d from, Entity to) {
        return lineOfSight(from, to.getEyePos());
    }
    public static boolean lineOfSight(Entity from, Entity to) {
        return lineOfSight(from.getEyePos(), to.getEyePos());
    }

    public static HitResult raycast(Vec3d from, Vec3d to) {
        RaycastContext context = new RaycastContext(from, to, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, SCMain.mc.player);
        return SCMain.mc.world.raycast(context);
    }
}
