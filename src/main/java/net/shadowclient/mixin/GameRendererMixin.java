package net.shadowclient.mixin;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.shadowclient.main.module.ModuleManager;
import net.shadowclient.mixininterface.IGameRenderer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin implements IGameRenderer {

    @Shadow
    void loadPostProcessor(Identifier id) {
    }

    @Override
    public void loadShader(@Nullable Identifier id) {
        if (id != null) {
            loadPostProcessor(id);
        } else {
            ((GameRenderer) (Object) this).disablePostProcessor();
        }
    }

    @Inject(at = @At("HEAD"), method = "tiltViewWhenHurt(Lnet/minecraft/client/util/math/MatrixStack;F)V", cancellable = true)
    private void onTiltViewWhenHurt(MatrixStack matrixStack, float f, CallbackInfo ci) {
        if (ModuleManager.getModule("notiltonhurt").enabled) {
            ci.cancel();
        }
    }
}
