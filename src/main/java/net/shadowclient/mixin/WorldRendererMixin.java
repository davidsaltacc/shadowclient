package net.shadowclient.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.shadowclient.main.event.EventManager;
import net.shadowclient.main.event.events.Render3DEvent;
import net.shadowclient.mixininterface.IWorldRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin implements IWorldRenderer {

    @Unique private MatrixStack matrices = null;
    @Unique private float tickDelta = 0;

    @Shadow private int regularEntityCount;

    @Inject(method = "render", at = @At("HEAD"))
    private void beforeRender(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci) {
        this.matrices = matrices;
        this.tickDelta = tickDelta;
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void afterRender(CallbackInfo ci) {
        EventManager.fireEvent(new Render3DEvent(matrices, tickDelta));
    }

    public int getRegularEntityCount() {
        return regularEntityCount;
    }

}
