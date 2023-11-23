package net.shadowclient.main.event.events;

import net.minecraft.world.chunk.WorldChunk;
import net.shadowclient.main.event.Event;

public class ChunkDataEvent extends Event {
    public final WorldChunk chunk;

    public ChunkDataEvent(WorldChunk chunk) {
        this.chunk = chunk;
    }
}
