package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.ui.CustomFont;
import net.minecraft.client.font.FontManager;
import net.minecraft.client.font.TextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FontManager.class)
public abstract class FontManagerMixin {

    @Inject(method = "createTextRenderer", at = @At("RETURN"))
    private void createTTFTextRenderer(CallbackInfoReturnable<TextRenderer> cir) {
        CustomFont.initTextRenderer();
    }

}
