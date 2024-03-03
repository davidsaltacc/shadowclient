package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.event.events.ShouldDrawSideEvent;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidRenderer.class)
public abstract class FluidRendererMixin {

    @Inject(method = "isSideCovered(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/Direction;FLnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z", at = @At("HEAD"), cancellable = true)
    private static void modifySideCovered(BlockView world, Direction direction, float height, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {

        ShouldDrawSideEvent evt = new ShouldDrawSideEvent(state, pos);

        EventManager.fireEvent(evt);

        if (evt.renderedSet) {
            cir.setReturnValue(evt.rendered);
        }

    }

}
