package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.annotations.ModdedMixin;
import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.event.events.ShouldDrawSideEvent;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@ModdedMixin(modId = "sodium")
@Mixin(
    targets = {
            "net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.DefaultFluidRenderer", // > v6
            "me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.FluidRenderer" // < v6
    },
    remap = false
)
public abstract class Sodium_FluidRendererMixin {

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "isSideExposed", at = @At("HEAD"), cancellable = true)
    private void modifyIsSideExposed(BlockRenderView world, int x, int y, int z, Direction dir, float height, CallbackInfoReturnable<Boolean> cir) {

        BlockState state = world.getBlockState(new BlockPos(x, y, z));
        BlockState state2 = world.getBlockState(new BlockPos(x, y, z).offset(dir));
        ShouldDrawSideEvent evt = new ShouldDrawSideEvent(state);

        EventManager.fireEvent(evt);

        if (evt.renderedSet) {
            cir.setReturnValue(evt.rendered && !state.getFluidState().getFluid().matchesType(state2.getFluidState().getFluid()));
        }
    }

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "isFullBlockFluidOccluded", at = @At("HEAD"), cancellable = true)
    private void modifyIsFullBlockFluidOccluded(BlockRenderView world, BlockPos pos, Direction dir, BlockState state, FluidState fluidState, CallbackInfoReturnable<Boolean> cir) {

        ShouldDrawSideEvent evt = new ShouldDrawSideEvent(state);

        EventManager.fireEvent(evt);

        if (evt.renderedSet) {
            cir.setReturnValue(!evt.rendered);
        }

    }

}
