package net.shadowclient.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import net.shadowclient.main.event.EventManager;
import net.shadowclient.main.event.events.ShouldDrawSideEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = {
    "me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.FluidRenderer", // > 5.0.0
    "me.jellysquid.mods.sodium.client.render.pipeline.FluidRenderer"}, // < 5.0.0
    remap = false)
public abstract class SodiumFluidRendererMixin {
    @Inject(at = @At("HEAD"), method = "isSideExposed", cancellable = true)
    private void isSideExposed(BlockRenderView world, int x, int y, int z, Direction dir, float height, CallbackInfoReturnable<Boolean> cir) {
        BlockPos pos = new BlockPos(x, y, z);
        BlockState state = world.getBlockState(pos);
        ShouldDrawSideEvent event = new ShouldDrawSideEvent(state, pos);
        EventManager.fireEvent(event);

        if (event.renderedSet) {
            cir.setReturnValue(event.rendered);
        }
    }
}