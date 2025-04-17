package net.justacoder.shadowclient.mixin;

import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Consumer;

@Mixin(SimpleOption.class)
public abstract class SimpleOptionMixin<T> {

    @Shadow
    T value;

    @Shadow
    @Final
    private Consumer<T> changeCallback;

}
