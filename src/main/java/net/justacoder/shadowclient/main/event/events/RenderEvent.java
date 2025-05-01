package net.justacoder.shadowclient.main.event.events;

import net.justacoder.shadowclient.main.render.Renderer;
import net.justacoder.shadowclient.main.event.Event;

public class RenderEvent extends Event {

    public final Renderer renderer;

    public RenderEvent(Renderer renderer) {
        this.renderer = renderer;
    }

}
