package net.shadowclient.mixininterface;


import net.minecraft.client.world.ClientEntityManager;
import net.minecraft.entity.Entity;

public interface IClientWorld {
    ClientEntityManager<Entity> getEntityManager();
}
