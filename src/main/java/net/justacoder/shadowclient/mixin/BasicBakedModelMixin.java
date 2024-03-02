package net.justacoder.shadowclient.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.BasicBakedModel;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.event.events.ShouldDrawSideEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.List;

@Mixin(BasicBakedModel.class)
public abstract class BasicBakedModelMixin {
    @Inject(at = @At("HEAD"), method = "getQuads", cancellable = true)
    private void onGetQuads(@Nullable BlockState state, @Nullable Direction face,
        Random random, CallbackInfoReturnable<List<BakedQuad>> cir)
    {
        if (face != null || state == null) {
            return;
        }

        ShouldDrawSideEvent event = new ShouldDrawSideEvent(state, null);
        EventManager.fireEvent(event);

        if (!event.rendered && event.renderedSet) {
            cir.setReturnValue(List.of());
        }
    }
}
