package net.justacoder.shadowclient.main.util;

import net.minecraft.entity.Entity;
import net.justacoder.shadowclient.mixin.EntityAccessor;

public abstract class EntityUtils {

    public static void setOnGround(Entity e, boolean onGround) {

        ((EntityAccessor) e).setOnGround(onGround);

    }

}
