package net.shadowclient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.shadowclient.main.module.ModuleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameOverlayRenderer.class)
public abstract class InGameOverlayRendererMixin {

    @ModifyConstant(method = "renderFireOverlay(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/util/math/MatrixStack;)V", constant = @Constant(floatValue = -0.3F))
    private static float modifyFireOffset(float original) {
        return original - ModuleManager.NoFireOverlayModule.getOffset();
    }

    @Inject(method = "renderUnderwaterOverlay", at = @At("HEAD"), cancellable = true)
    private static void onRenderUnderwaterOverlay(MinecraftClient client, MatrixStack matrices, CallbackInfo ci) {
        if (ModuleManager.NoOverlayModule.enabled) {
            ci.cancel();
        }
    }
}
