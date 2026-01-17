package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.LifecyclePoints;
import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.render.font.Font;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;onFontOptionsChanged()V", shift = At.Shift.AFTER))
    private void initCustomFont(RunArgs args, CallbackInfo ci) {
        Font.initializeFont();
    }

    @Inject(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;instance:Lnet/minecraft/client/MinecraftClient;", shift = At.Shift.AFTER, opcode = Opcodes.PUTSTATIC))
    private void instanceSet(RunArgs args, CallbackInfo ci) {
        LifecyclePoints.minecraftClientCreated();
    }

}
