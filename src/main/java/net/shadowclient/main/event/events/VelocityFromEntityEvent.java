package net.shadowclient.main.event.events;

import net.minecraft.entity.Entity;
import net.shadowclient.main.event.Event;

public class VelocityFromEntityEvent extends Event {
    public final Entity entity;
    public VelocityFromEntityEvent(Entity entity) {
        this.entity = entity;
    }
}