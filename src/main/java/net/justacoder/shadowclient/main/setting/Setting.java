package net.justacoder.shadowclient.main.setting;

import net.justacoder.shadowclient.main.translations.TranslatableString;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class Setting {

    public final TranslatableString name;

    public Setting(TranslatableString name) {
        this.name = name;
    }

    public List<BiConsumer<Object, Object>> callbacks = new ArrayList<>();

    private boolean callCallbacks = true;

    public void addChangeCallback(BiConsumer<Object, Object> cb) {
        callbacks.add(cb);
    }

    public void callCallbacks(Object newValue, Object oldValue) {
        if (callCallbacks) {
            callbacks.forEach(cb -> cb.accept(newValue, oldValue));
        }
    }

    public void shouldCallCallbacks(boolean call) {
        callCallbacks = call;
    }

    public boolean getShouldCallCallbacks() {
        return callCallbacks;
    }

    public abstract void reset();

}

