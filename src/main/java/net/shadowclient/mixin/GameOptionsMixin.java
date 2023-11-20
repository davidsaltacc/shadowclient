package net.shadowclient.mixin;

import com.google.common.collect.Lists;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.shadowclient.main.SCMain;
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
    @Mutable
    @Final
    @Shadow
    public KeyBinding[] allKeys;

    @Inject(at = @At("HEAD"), method = "load()V")
    public void onLoad(CallbackInfo info) {
        List<KeyBinding> binds = Lists.newArrayList(allKeys);
        binds.addAll(SCMain.keyBindings);
        allKeys = binds.toArray(new KeyBinding[0]);
    }
}
