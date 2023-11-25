package net.shadowclient.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import net.shadowclient.main.event.EventManager;
import net.shadowclient.main.event.events.ShouldDrawSideEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidRenderer.class)
public abstract class FluidRendererMixin {

    @Inject(method = "shouldRenderSide", at = @At("HEAD"), cancellable = true)
    private static void onShouldRenderSide(BlockRenderView world, BlockPos pos, FluidState fluidState, BlockState blockState, Direction direction, FluidState neighborFluidState, CallbackInfoReturnable<Boolean> cir) {
        ShouldDrawSideEvent evt = new ShouldDrawSideEvent(blockState, pos);
        EventManager.fireEvent(evt);

        if (evt.renderedSet) {
            cir.setReturnValue(evt.rendered);
        }
    }

}
