package net.justacoder.shadowclient.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.ui.hud.HudRenderer;
import net.minecraft.client.MinecraftClient;
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
    private void onRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (ShadowClientMain.mc.currentScreen == null && ModuleManager.ShadowHudModule.enabled && !((DebugHudAccessor) ShadowClientMain.mc.getDebugHud()).debugEnabled() && MinecraftClient.isHudEnabled()) {
            HudRenderer.onHudRender(context, tickCounter.getTickDelta(false));
        }
    }

    @Inject(method = "renderMainHud", at = @At("HEAD"), cancellable = true)
    private void beforeRenderMainHud(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (ModuleManager.FreecamModule.enabled) {
            ci.cancel();
        }
    }

    @Inject(method = "renderExperienceLevel", at = @At("HEAD"), cancellable = true)
    private void beforeRenderExpLevel(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) { // why does the experience level get drawn separately
        if (ModuleManager.FreecamModule.enabled) {
            ci.cancel();
        }
    }
}
