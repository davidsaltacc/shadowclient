package net.justacoder.shadowclient.main.event.events;

import net.justacoder.shadowclient.main.event.Event;

public class MouseMoveEvent extends Event {

    public double getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(double deltaY) {
        this.deltaY = deltaY;
    }

    public double getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(double deltaX) {
        this.deltaX = deltaX;
    }

    private double deltaX;
    private double deltaY;

    public MouseMoveEvent(double dX, double dY) {
        deltaX = dX;
        deltaY = dY;
    }

}
