package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.render.font.Font;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;onFontOptionsChanged()V", shift = At.Shift.AFTER))
    private void initCustomFont(RunArgs args, CallbackInfo ci) {
        Font.initializeFont();
    }

}
