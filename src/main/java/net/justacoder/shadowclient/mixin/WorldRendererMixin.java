package net.justacoder.shadowclient.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.event.events.RenderEvent;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.render.BufferBuilderProvider;
import net.justacoder.shadowclient.main.render.Renderer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.Handle;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.profiler.Profiler;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Unique private MatrixStack stack;
    @Unique private float delta;

    @Inject(method = "method_62214", at = @At("TAIL"))
    private void afterRenderMain(Fog fog, RenderTickCounter renderTickCounter, Camera camera, Profiler profiler, Matrix4f matrix4f, Matrix4f matrix4f2, Handle handle, Handle handle2, Handle handle3, Handle handle4, boolean bl, Frustum frustum, Handle handle5, CallbackInfo ci, @Local MatrixStack stack, @Local float delta) {
        this.stack = stack;
        this.delta = delta;
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void afterRender(ObjectAllocator allocator, RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, Matrix4f positionMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
        BufferBuilderProvider provider = BufferBuilderProvider.getInstance();
        EventManager.fireEvent(new RenderEvent(new Renderer(provider, stack, delta)));
        provider.draw();
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/BackgroundRenderer;applyFog(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/BackgroundRenderer$FogType;Lorg/joml/Vector4f;FZF)Lnet/minecraft/client/render/Fog;"), index = 3)
    private float spoofFogDist(float viewDistance) {
        return ModuleManager.SpoofRenderDistanceModule.getDistanceBlocks((int) viewDistance);
    }

}
