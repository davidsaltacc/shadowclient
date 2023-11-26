package net.shadowclient.main.event.events;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.shadowclient.main.event.Event;

public class ShouldDrawSideEvent extends Event {
    public boolean renderedSet = false;
    public boolean rendered;

    public BlockState state;
    public BlockPos pos;

    public ShouldDrawSideEvent(BlockState s, BlockPos p) {
        state = s;
        pos = p;
    }

    public void setRendered(boolean r) {
        rendered = r;
        renderedSet = true;
    }

    public void unsetRendered() {
        renderedSet = false;
    }
}
