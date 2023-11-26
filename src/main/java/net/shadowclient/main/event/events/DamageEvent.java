package net.shadowclient.main.event.events;

import net.minecraft.entity.damage.DamageSource;
import net.shadowclient.main.event.Event;

public class DamageEvent extends Event {
    public DamageSource source;
    public float amount;

    public DamageEvent(DamageSource src, float amt) {
        this.source = src;
        this.amount = amt;
    }
}
