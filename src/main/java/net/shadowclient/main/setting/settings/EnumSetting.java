package net.shadowclient.main.setting.settings;

import net.shadowclient.main.setting.Setting;

public class EnumSetting<E extends Enum<E>> extends Setting {

    private E enumValue;

    public EnumSetting(String name, E defaultEnumValue) {
        super(name);
        this.enumValue = defaultEnumValue;
    }

    public void setEnumValue(E value) {
        this.enumValue = value;
        callCallbacks();
    }
    public E getEnumValue() {
        return this.enumValue;
    }
}
