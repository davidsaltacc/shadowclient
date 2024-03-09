package net.justacoder.shadowclient.main.event.events;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.justacoder.shadowclient.main.event.Event;

public class ShouldDrawSideEvent extends Event {
    public boolean renderedSet = false;
    public boolean rendered;

    public BlockState state;

    public ShouldDrawSideEvent(BlockState s) {
        state = s;
    }

    public void setRendered(boolean r) {
        rendered = r;
        renderedSet = true;
    }

    public void unsetRendered() {
        renderedSet = false;
    }
}
