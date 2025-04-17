package net.justacoder.shadowclient.main.event.events;

import net.minecraft.entity.Entity;
import net.justacoder.shadowclient.main.event.Event;

public class VelocityFromFluidEvent extends Event {
    public final Entity entity;
    public VelocityFromFluidEvent(Entity entity) {
        this.entity = entity;
    }
}