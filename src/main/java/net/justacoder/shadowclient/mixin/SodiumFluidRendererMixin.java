package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.event.events.ShouldDrawSideEvent;
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
    targets = {"me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.FluidRenderer"},
    remap = false
)
public abstract class SodiumFluidRendererMixin {

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "isSideExposed", at = @At("HEAD"), cancellable = true)
    private void modifyIsSideExposed(BlockRenderView world, int x, int y, int z, Direction dir, float height, CallbackInfoReturnable<Boolean> cir) {

        BlockPos pos = new BlockPos(x, y, z);
        ShouldDrawSideEvent evt = new ShouldDrawSideEvent(world.getBlockState(pos));

        EventManager.fireEvent(evt);

        if (evt.renderedSet) {
            cir.setReturnValue(evt.rendered);
        }
    }

}
