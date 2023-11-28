package net.shadowclient.main.util;

import net.minecraft.entity.Entity;
import net.shadowclient.mixin.EntityAccessor;

public class EntityUtils {

    public static void setOnGround(Entity e, boolean onGround) {

        ((EntityAccessor) e).setOnGround(onGround);

    }

}
