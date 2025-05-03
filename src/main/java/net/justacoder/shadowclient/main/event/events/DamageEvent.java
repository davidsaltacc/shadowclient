package net.justacoder.shadowclient.main.event.events;

import net.justacoder.shadowclient.main.event.Event;

public class DamageEvent extends Event {

    public float amount;

    public DamageEvent(float amt) {
        this.amount = amt;
    }

}
