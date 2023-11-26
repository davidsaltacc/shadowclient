package net.shadowclient.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.shadowclient.main.event.EventManager;
import net.shadowclient.main.event.events.ShouldDrawSideEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = {
    "me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockOcclusionCache", // > 5.0.0
    "me.jellysquid.mods.sodium.client.render.occlusion.BlockOcclusionCache"}, // < 5.0.0
    remap = false)
public abstract class SodiumBlockOcclusionCacheMixin {
    @Inject(at = @At("HEAD"), method = "shouldDrawSide", cancellable = true, remap = false)
    private void shouldDrawSide(BlockState state, BlockView reader, BlockPos pos, Direction face, CallbackInfoReturnable<Boolean> cir) {
        ShouldDrawSideEvent event = new ShouldDrawSideEvent(state, pos);

        EventManager.fireEvent(event);

        if (event.renderedSet) {
            cir.setReturnValue(event.rendered);
        }
    }
}