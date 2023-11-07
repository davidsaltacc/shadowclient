package net.shadowclient.mixin;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.shadowclient.main.module.ModuleManager;
import net.shadowclient.mixininterface.IGameRenderer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
        if (ModuleManager.NoTiltOnHurtModule.enabled) {
            ci.cancel();
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F", ordinal = 0), method = "renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V")
    private float wurstNauseaLerp(float delta, float start, float end) {
        if (ModuleManager.NoWobbleModule.enabled) {
            return 0;
        }

        return MathHelper.lerp(delta, start, end);
    }

    @Inject(at = @At("HEAD"), method = "getNightVisionStrength(Lnet/minecraft/entity/LivingEntity;F)F", cancellable = true)
    private static void onGetNightVisionStrength(LivingEntity entity, float tickDelta, CallbackInfoReturnable<Float> cir) {
        if (ModuleManager.NightVisionModule.enabled) {
            cir.setReturnValue(1f);
        }
    }
}
