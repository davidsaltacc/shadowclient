package net.justacoder.shadowclient.main.event.events;

import net.minecraft.client.util.math.MatrixStack;
import net.justacoder.shadowclient.main.event.Event;
import org.joml.Matrix4fStack;

public class Render3DEvent extends Event {
    public final Matrix4fStack matrices;
    public final float tickDelta;
    public Render3DEvent(Matrix4fStack matrices, float tickDelta) {
        this.matrices = matrices;
        this.tickDelta = tickDelta;
    }
}
