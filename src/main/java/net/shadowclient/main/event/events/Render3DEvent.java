package net.shadowclient.main.event.events;

import net.minecraft.client.util.math.MatrixStack;
import net.shadowclient.main.event.Event;

public class Render3DEvent extends Event {
    public final MatrixStack matrices;
    public final float tickDelta;
    public Render3DEvent(MatrixStack matrices, float tickDelta) {
        this.matrices = matrices;
        this.tickDelta = tickDelta;
    }
}
