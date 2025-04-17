package net.justacoder.shadowclient.main.event.events;

import net.justacoder.shadowclient.main.event.Event;

public class MouseClickedEvent extends Event {

    public int button;

    public MouseClickedEvent(int button) {
        this.button = button;
    }

}
