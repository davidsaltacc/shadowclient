package net.shadowclient.mixininterface;

import net.minecraft.client.option.SimpleOption;

public interface ISimpleOption<T> {
    void forceSet(T value);


    @SuppressWarnings("unchecked")
    static <T> ISimpleOption<T> getFromOption(SimpleOption<T> option) {
        return (ISimpleOption<T>) (Object) option;
    }
}
