package net.shadowclient.main.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.shadowclient.main.SCMain;
import net.shadowclient.mixininterface.IEntity;
import java.util.UUID;

public class FakePlayerEntity extends OtherClientPlayerEntity {

    public ClientPlayerEntity player = SCMain.mc.player;
    public ClientWorld world = SCMain.mc.world;

    public FakePlayerEntity() {
        super(SCMain.mc.world, new GameProfile(UUID.randomUUID(), SCMain.mc.player.getGameProfile().getName()));

        getGameProfile().getProperties().putAll(SCMain.mc.player.getGameProfile().getProperties());

        copyPositionAndRotation(player);
        copyInventory();
        copyPlayerModel(player, this);
        copyRotation();
    }

    public void copyInventory() {
        getInventory().clone(player.getInventory());
    }

    public void copyPlayerModel(Entity from, Entity to) {
        DataTracker fromT = from.getDataTracker();
        DataTracker toT = to.getDataTracker();
        Byte playerModel = fromT.get(PLAYER_MODEL_PARTS);
        toT.set(PLAYER_MODEL_PARTS, playerModel);
    }

    public void copyRotation() {
        headYaw = player.headYaw;
        bodyYaw = player.bodyYaw;
    }

    public void spawn() {
        world.addEntity(((IEntity) this).getCurrentId().incrementAndGet(), this);
    }

    public void despawn() {
        discard();
    }

    public void resetPlayerPosition() {
        player.refreshPositionAndAngles(getX(), getY(), getZ(), getYaw(), getPitch());
    }
}
