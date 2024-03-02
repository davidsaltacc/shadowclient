package net.justacoder.shadowclient.mixin;

import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import net.justacoder.shadowclient.main.module.ModuleManager;
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
        if (ModuleManager.XRayModule.enabled) {
            cir.setReturnValue(true);
        }
    }

}
