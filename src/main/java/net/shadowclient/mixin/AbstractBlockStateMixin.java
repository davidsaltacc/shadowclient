package net.shadowclient.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.shadowclient.main.event.EventManager;
import net.shadowclient.main.event.events.GetAmbientOcclusionLightLevelEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {
    @Inject(at = @At("TAIL"), method = "getAmbientOcclusionLightLevel(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F", cancellable = true)
    private void onGetAmbientOcclusionLightLevel(BlockView blockView, BlockPos blockPos, CallbackInfoReturnable<Float> cir) {
        GetAmbientOcclusionLightLevelEvent event = new GetAmbientOcclusionLightLevelEvent();

        EventManager.fireEvent(event);

        if (event.set) {
            cir.setReturnValue((float) event.lightLevel);
        }
    }
}
