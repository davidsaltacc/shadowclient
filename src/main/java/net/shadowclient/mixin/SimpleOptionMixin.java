package net.shadowclient.mixin;

import net.minecraft.client.option.SimpleOption;
import net.shadowclient.main.SCMain;
import net.shadowclient.mixininterface.ISimpleOption;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import java.util.Objects;
import java.util.function.Consumer;

@Mixin(SimpleOption.class)
public abstract class SimpleOptionMixin<T> implements ISimpleOption<T> {

    @Shadow
    T value;

    @Shadow
    @Final
    private Consumer<T> changeCallback;

    @Override
    public void forceSet(T newValue) {
        if (!SCMain.mc.isRunning()) {
            value = newValue;
            return;
        }

        if (!Objects.equals(value, newValue)) {
            value = newValue;
            changeCallback.accept(value);
        }
    }
}
