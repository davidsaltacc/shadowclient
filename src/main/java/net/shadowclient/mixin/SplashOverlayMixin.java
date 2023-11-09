package net.shadowclient.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.shadowclient.main.config.SCSettings;
import net.shadowclient.main.util.ColorUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SplashOverlay.class)
public abstract class SplashOverlayMixin {

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/SplashOverlay;withAlpha(II)I"))
    private int onWithAlpha(int color, int alpha) {
        return SCSettings.LOADING_SCREEN_BGND_COLOR;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_clearColor(FFFF)V"))
    private void onClearColor(float red, float green, float blue, float alpha) {
        int[] c = ColorUtils.int2RGBA(SCSettings.LOADING_SCREEN_BGND_COLOR);
        float[] cf = ColorUtils.RGBIntToRGBFloat(c[0], c[1], c[2]);
        GlStateManager._clearColor(cf[0], cf[1], cf[2], 1.0f);
    }
}
