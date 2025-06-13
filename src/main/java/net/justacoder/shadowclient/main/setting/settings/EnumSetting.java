package net.justacoder.shadowclient.main.setting.settings;

import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.translations.TranslatableString;

public class EnumSetting<E extends Enum<E>> extends Setting {

    private E enumValue;

    public EnumSetting(TranslatableString name, E defaultEnumValue) {
        super(name);
        this.enumValue = defaultEnumValue;
    }

    public void setEnumValue(E value) {
        E old = enumValue;
        this.enumValue = value;
        callCallbacks(value, old);
    }
    public E getEnumValue() {
        return this.enumValue;
    }
}
