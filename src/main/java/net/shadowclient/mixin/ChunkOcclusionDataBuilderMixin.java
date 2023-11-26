package net.shadowclient.mixin;

import net.minecraft.client.render.chunk.ChunkOcclusionDataBuilder;
import net.minecraft.util.math.BlockPos;
import net.shadowclient.main.event.EventManager;
import net.shadowclient.main.event.events.SetOpaqueCubeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkOcclusionDataBuilder.class)
public abstract class ChunkOcclusionDataBuilderMixin {
    @Inject(at = @At("HEAD"), method = "markClosed(Lnet/minecraft/util/math/BlockPos;)V", cancellable = true)
    private void onMarkClosed(BlockPos pos, CallbackInfo ci) {
        SetOpaqueCubeEvent event = new SetOpaqueCubeEvent();
        EventManager.fireEvent(event);

        if (event.cancelled) {
            ci.cancel();
        }
    }
}
