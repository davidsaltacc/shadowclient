package net.shadowclient.main.util;

import net.minecraft.entity.Entity;
import net.shadowclient.mixininterface.IEntity;

public class EntityUtils {

    public static void setOnGround(Entity e, boolean onGround) {

        ((IEntity) e).setOnGround(onGround);

    }

}
