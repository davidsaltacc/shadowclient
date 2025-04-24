package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.module.ModuleManager;
import net.minecraft.client.render.BuiltChunkStorage;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BuiltChunkStorage.class)
public abstract class BuiltChunkStorageMixin {

    @Shadow @Final protected World world;
    @Shadow private ChunkSectionPos sectionPos;
    @Shadow private int viewDistance;

    @Inject(method = "isSectionWithinViewDistance", at = @At("HEAD"), cancellable = true)
    private void changeViewDistance(int sectionX, int sectionY, int sectionZ, CallbackInfoReturnable<Boolean> cir) {
        int spoofedViewDistance = ModuleManager.SpoofRenderDistanceModule.getDistanceChunks(viewDistance);
        if (sectionY >= world.getBottomSectionCoord() && sectionY <= this.world.getTopSectionCoord()) {
            cir.setReturnValue(sectionX >= sectionPos.getSectionX() - spoofedViewDistance && sectionX <= this.sectionPos.getSectionX() + spoofedViewDistance && sectionZ >= this.sectionPos.getSectionZ() - spoofedViewDistance && sectionZ <= this.sectionPos.getSectionZ() + spoofedViewDistance);
        } else {
            cir.setReturnValue(false);
        }
    }

}
