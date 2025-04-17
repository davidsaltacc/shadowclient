package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.event.events.ShouldDrawSideEvent;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidRenderer.class)
public abstract class FluidRendererMixin {

    @Inject(method = "isSideCovered", at = @At("HEAD"), cancellable = true)
    private static void modifySideCovered(Direction direction, float f, BlockState blockState, CallbackInfoReturnable<Boolean> cir) {

        ShouldDrawSideEvent evt = new ShouldDrawSideEvent(blockState);

        EventManager.fireEvent(evt);

        if (evt.renderedSet) {
            cir.setReturnValue(evt.rendered);
        }

    }

}
