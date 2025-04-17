package net.justacoder.shadowclient.main.event;

public abstract class Event {
    public boolean cancelled = false;
    public void cancel() {
        this.cancelled = true;
    }
    public void uncancel() {
        this.cancelled = false;
    }
}
