package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.config.SCSettings;
import net.justacoder.shadowclient.main.ui.SCSplashTexts;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SplashTextRenderer.class)
public abstract class SplashTextRendererMixin {

    @Unique
    private String customText;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void setCustomText(String text, CallbackInfo ci) {
        customText = SCSplashTexts.getRandom();
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void addSplashText(DrawContext context, int screenWidth, TextRenderer textRenderer, int alpha, CallbackInfo ci) {

        if (SCSettings.VanillaSpoof.booleanValue()) {
            return;
        }

        context.getMatrices().push();
        context.getMatrices().translate(screenWidth / 2.0f + 123.0f + 10.f, 69.0f + 10.f, 0.0f);
        context.getMatrices().multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-20.0f));
        float f = 1.8f - MathHelper.abs(MathHelper.sin((Util.getMeasuringTimeMs() % 1000L) / 1000.0f * (float) (Math.PI * 2) + (float) Math.PI / 2.f) * 0.1f);
        f = f * 75.f / (textRenderer.getWidth(customText) + 32);
        context.getMatrices().scale(f, f, f);
        context.drawCenteredTextWithShadow(textRenderer, customText, 0, -8, 16776960 | alpha);
        context.getMatrices().pop();

    }

}
