package net.justacoder.shadowclient.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.event.events.ShouldDrawSideEvent;
import net.justacoder.shadowclient.main.module.ModuleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Inject(at = @At("HEAD"), method = "getVelocityMultiplier", cancellable = true)
    private void onGetVelocityMultiplier(CallbackInfoReturnable<Float> cir) {
        if (!ModuleManager.NoSlowdownModule.enabled) {
            return;
        }

        if (cir.getReturnValueF() < 1) {
            cir.setReturnValue(1f);
        }
    }

    @Inject(at = @At("HEAD"), method = "shouldDrawSide", cancellable = true, remap = false)
    private static void shouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction side, BlockPos otherPos, CallbackInfoReturnable<Boolean> cir) {
        ShouldDrawSideEvent event = new ShouldDrawSideEvent(state);

        EventManager.fireEvent(event);

        if (event.renderedSet) {
            cir.setReturnValue(event.rendered);
        }
    }
}
