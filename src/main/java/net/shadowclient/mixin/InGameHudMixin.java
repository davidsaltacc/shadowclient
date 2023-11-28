package net.shadowclient.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import net.shadowclient.main.SCMain;
import net.shadowclient.main.module.ModuleManager;
import net.shadowclient.main.ui.hud.HudRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Inject(method = "renderOverlay", at = @At("HEAD"), cancellable = true)
    private void onRenderOverlay(DrawContext context, Identifier texture, float opacity, CallbackInfo ci) {
        if (texture == null || !"textures/misc/pumpkinblur.png".equals(texture.getPath())) {
            return;
        }

        if (ModuleManager.NoPumkinModule.enabled) {
            ci.cancel();
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (SCMain.mc.currentScreen == null && ModuleManager.ShadowHudModule.enabled && !SCMain.mc.options.debugEnabled) {
            HudRenderer.onHudRender(context, tickDelta);
        }
    }
}
