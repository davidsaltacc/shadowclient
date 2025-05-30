package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.module.ModuleManager;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Fog;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {

    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void removeFog(Camera camera, BackgroundRenderer.FogType fogType, Vector4f color, float viewDistance, boolean thickenFog, float tickDelta, CallbackInfoReturnable<Fog> cir) {
        if (ModuleManager.NoFogModule.enabled && (fogType != BackgroundRenderer.FogType.FOG_SKY || ModuleManager.NoFogModule.SKY.booleanValue())) {
            cir.setReturnValue(Fog.DUMMY);
        }
    }

}
