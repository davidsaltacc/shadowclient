package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.event.events.ShouldDrawSideEvent;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(
    targets = {"me.pepperbell.continuity.client.model.CullingCache"},
    remap = false
)
public abstract class ContinuityCullingCacheMixin {

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "shouldCull(Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z", at = @At("HEAD"), cancellable = true)
    private void shouldCull(BlockRenderView blockView, BlockPos pos, BlockState state, Direction cullFace, CallbackInfoReturnable<Boolean> cir) {

        ShouldDrawSideEvent evt = new ShouldDrawSideEvent(state, pos);

        EventManager.fireEvent(evt);

        if (evt.renderedSet) {
            cir.setReturnValue(!evt.rendered);
        }
    }

}
