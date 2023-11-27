package net.shadowclient.mixininterface;


import net.minecraft.client.world.ClientEntityManager;
import net.minecraft.entity.Entity;

public interface IClientWorld { // todo make all mixininterface into accessors if possible
    ClientEntityManager<Entity> getEntityManager();
}
