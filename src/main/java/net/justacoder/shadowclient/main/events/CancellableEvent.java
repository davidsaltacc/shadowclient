package net.justacoder.shadowclient.main.events;

public abstract class CancellableEvent extends Event {

    private boolean cancelled = false;

    public void cancel() {
        this.cancelled = true;
    }

    public void uncancel() {
        this.cancelled = false;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

}
