package net.justacoder.shadowclient.main.event.events;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.justacoder.shadowclient.main.event.Event;
import java.util.Map;

public class ChunkDeltaUpdateEvent extends Event {
    public final Map<BlockPos, BlockState> delta;

    public ChunkDeltaUpdateEvent(Map<BlockPos, BlockState> delta) {
        this.delta = delta;
    }
}
