package net.justacoder.shadowclient.main.event.events;

import net.justacoder.shadowclient.main.event.Event;

public class ClipAtLedgeEvent extends Event {
    public int clip; // 0 = unset, 1 = false, 2 = true

    public ClipAtLedgeEvent() {
        clip = 0;
    }

    public void setClip() {
        clip = 2;
    }

    public void unsetClip() {
        clip = 1;
    }
}
