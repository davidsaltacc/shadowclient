package net.shadowclient.main.event.events;

import net.minecraft.block.entity.BlockEntity;
import net.shadowclient.main.event.Event;

public class RenderBlockEntityEvent extends Event {
    public BlockEntity blockEntity;

    public RenderBlockEntityEvent(BlockEntity be) {
        blockEntity = be;
    }
}
