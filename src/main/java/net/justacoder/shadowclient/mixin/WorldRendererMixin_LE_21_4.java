package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.annotations.VersionDependentMixin;
import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.event.events.RenderEvent;
import net.justacoder.shadowclient.main.render.BufferBuilderProvider;
import net.justacoder.shadowclient.main.render.Renderer;
import net.justacoder.shadowclient.main.util.MixinSharedValues;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.ObjectAllocator;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@VersionDependentMixin(mcVersionPredicate = ">=1.21.4")
@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin_LE_21_4 {

    @Inject(method = "render", at = @At("TAIL"))
    private void afterRender(ObjectAllocator allocator, RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, Matrix4f positionMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
        BufferBuilderProvider provider = BufferBuilderProvider.getInstance();
        EventManager.fireEvent(new RenderEvent(new Renderer(provider, MixinSharedValues.worldRendererStack, MixinSharedValues.worldRendererDelta)));
        provider.draw();
    }

}
