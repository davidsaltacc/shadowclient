package net.justacoder.shadowclient.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.*;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.event.events.Render3DEvent;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Unique private Matrix4fStack matrices = null;
    @Unique private float tickDelta = 0;

    @Inject(method = "render", at = @At("HEAD"))
    private void beforeRender(ObjectAllocator allocator, RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, Matrix4f positionMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
        this.tickDelta = tickCounter.getTickDelta(false);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/FrameGraphBuilder;createObjectNode(Ljava/lang/String;Ljava/lang/Object;)Lnet/minecraft/client/util/Handle;", ordinal = 0, shift = At.Shift.AFTER))
    private void whileRender(ObjectAllocator allocator, RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, Matrix4f positionMatrix, Matrix4f projectionMatrix, CallbackInfo ci, @Local Matrix4fStack matrixStack) {
        this.matrices = matrixStack;
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void afterRender(CallbackInfo ci) {
        EventManager.fireEvent(new Render3DEvent(matrices, tickDelta));
    }
}
