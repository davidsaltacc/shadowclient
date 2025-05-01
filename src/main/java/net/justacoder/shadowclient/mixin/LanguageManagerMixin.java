package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LanguageManager.class)
public abstract class LanguageManagerMixin {

    @Inject(method = "reload", at = @At("RETURN"))
    private void onLanguageReloaded(ResourceManager manager, CallbackInfo ci) {
        ShadowClientMain.reloadTranslations();
    }

}
