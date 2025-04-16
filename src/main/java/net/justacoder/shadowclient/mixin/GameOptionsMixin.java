package net.justacoder.shadowclient.mixin;

import com.google.common.collect.Lists;
import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.event.events.PerspectiveChangeEvent;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.justacoder.shadowclient.main.SCMain;
import net.minecraft.client.option.Perspective;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {

    @Mutable @Final @Shadow public KeyBinding[] allKeys;

    @Inject(method = "load", at = @At("HEAD"))
    public void onLoad(CallbackInfo info) {
        List<KeyBinding> binds = Lists.newArrayList(allKeys);
        binds.addAll(SCMain.keyBindings);
        allKeys = binds.toArray(new KeyBinding[0]);
    }

}
