package net.justacoder.shadowclient.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.event.events.Render3DEvent;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Unique private MatrixStack matrices = null;
    @Unique private float tickDelta = 0;

    @Inject(method = "render", at = @At("HEAD"))
    private void beforeRender(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci) {
        this.matrices = matrices;
        this.tickDelta = tickDelta;
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void afterRender(CallbackInfo ci) {
        EventManager.fireEvent(new Render3DEvent(matrices, tickDelta));
    }
}
