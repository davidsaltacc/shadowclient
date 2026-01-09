package net.justacoder.shadowclient.main.events;

import net.justacoder.shadowclient.main.keybinds.Key;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class KeyEvent implements Event {

    public final Key key;
    public final int action;

    public KeyEvent(int keyCode, int scanCode, int action, int modifiers) {
        this.key = new Key(keyCode, scanCode, modifiers);
        this.action = action;
    }

    // --------- copy paste below part into any new event ---------
    private static final CopyOnWriteArrayList<Consumer<KeyEvent>> listeners = new CopyOnWriteArrayList<>();
    public static void subscribe(Consumer<KeyEvent> l) {
        listeners.add(l);
    }
    private boolean fired = false;
    public final KeyEvent fire() {
        if (fired) {
            throw new RuntimeException("Tried to fire event that was already fired");
        }
        fired = true;
        for (var l : listeners) {
            l.accept(this);
        }
        return this;
    }

}
