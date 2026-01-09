package net.justacoder.shadowclient.main.events;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class KeyEvent extends Event {

    public final int keyCode;
    public final int scanCode;
    public final int action;
    public final int modifiers;

    public KeyEvent(int keyCode, int scanCode, int action, int modifiers) {
        this.keyCode = keyCode;
        this.scanCode = scanCode;
        this.action = action;
        this.modifiers = modifiers;
    }

    private static final CopyOnWriteArrayList<Consumer<KeyEvent>> listeners = new CopyOnWriteArrayList<>();

    public static void subscribe(Consumer<KeyEvent> l) {
        listeners.add(l);
    }

    public final KeyEvent fire() {
        for (var l : listeners) {
            l.accept(this);
        }
        return this;
    }

}
