package net.shadowclient.main.event.events;

import net.shadowclient.main.event.Event;

public class KnockbackEvent extends Event {
    public double x, y, z;
    public KnockbackEvent(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
