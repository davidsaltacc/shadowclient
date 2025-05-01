package net.justacoder.shadowclient.mixin;

import com.google.common.collect.Lists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.justacoder.shadowclient.main.ShadowClientMain;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {

    @Mutable @Final @Shadow public KeyBinding[] allKeys;

    @Shadow protected MinecraftClient client;

    @Inject(method = "load", at = @At("HEAD"))
    public void onLoad(CallbackInfo info) {

        List<KeyBinding> binds = Lists.newArrayList(allKeys);
        binds.addAll(ShadowClientMain.keyBindings);
        allKeys = binds.toArray(new KeyBinding[0]);

    }

}
