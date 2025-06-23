package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.annotations.VersionDependentMixin;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@VersionDependentMixin(mcVersionPredicate = ">=1.21.4")
@Mixin(InGameOverlayRenderer.class)
public abstract class InGameOverlayRendererMixin_LE_21_4 {

    @Inject(method = "renderUnderwaterOverlay", at = @At("HEAD"), cancellable = true)
    private static void onRenderUnderwaterOverlay(MinecraftClient client, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        if (ModuleManager.NoOverlayModule.enabled) {
            ci.cancel();
        }
    }

}
