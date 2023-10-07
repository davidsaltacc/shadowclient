package net.shadowclient.main.event.events;

import net.shadowclient.main.event.Event;

public class KeyPressEvent extends Event {
    public final int keyCode;
    public final int scanCode;
    public final int action;
    public final int modifiers;

    public KeyPressEvent(int keyCode, int scanCode, int action, int modifiers) {
        this.keyCode = keyCode;
        this.scanCode = scanCode;
        this.action = action;
        this.modifiers = modifiers;
    }
}