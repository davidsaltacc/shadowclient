package net.justacoder.shadowclient.main.event.events;

import net.minecraft.block.entity.BlockEntity;
import net.justacoder.shadowclient.main.event.Event;

public class RenderBlockEntityEvent extends Event {
    public BlockEntity blockEntity;

    public RenderBlockEntityEvent(BlockEntity be) {
        blockEntity = be;
    }
}
