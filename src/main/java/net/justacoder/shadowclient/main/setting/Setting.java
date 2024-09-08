package net.justacoder.shadowclient.main.setting;

import java.util.ArrayList;
import java.util.List;

public abstract class Setting {

    public final String name;

    public Setting(String name) {
        this.name = name;
    }

    public List<Runnable> callbacks = new ArrayList<>();

    private boolean callCallbacks = true;

    public void addChangeCallback(Runnable cb) {
        callbacks.add(cb);
    }

    public void callCallbacks() {
        if (callCallbacks) {
            callbacks.forEach(Runnable::run);
        }
    }

    public void shouldCallCallbacks(boolean call) {
        callCallbacks = call;
    }
}

