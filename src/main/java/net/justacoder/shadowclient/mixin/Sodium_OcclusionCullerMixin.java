package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.module.ModuleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Pseudo
@Mixin(
    targets = {
            "net.caffeinemc.mods.sodium.client.render.chunk.occlusion.OcclusionCuller", // > v6
            "me.jellysquid.mods.sodium.client.render.chunk.occlusion.OcclusionCuller" // < v6
    },
    remap = false
)
public abstract class Sodium_OcclusionCullerMixin {

    @SuppressWarnings("UnresolvedMixinReference")
    @ModifyVariable(method = "isWithinRenderDistance", at = @At("HEAD"), ordinal = 0)
    private static float spoofRenderDistance(float original) {
        return ModuleManager.SpoofRenderDistanceModule.getDistanceBlocks((int) original);
    }

}
