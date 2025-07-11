package net.justacoder.shadowclient.main.setting.settings;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.setting.SettingEnum;
import net.justacoder.shadowclient.main.translations.TranslatableString;

@SuppressWarnings("unchecked")
public class EnumSetting<E extends Enum<E>> extends Setting {

    private E enumValue;
    private E defaultValue;

    public EnumSetting(TranslatableString name, E defaultEnumValue) {
        super(name);
        if (!(defaultEnumValue instanceof SettingEnum)) {
            throw new RuntimeException("Tried to create enum setting, but provided an enum that doesn't implement SettingEnum.");
        }
        this.enumValue = defaultEnumValue;
        this.defaultValue = defaultEnumValue;
    }

    public void setEnumValue(E value) {
        E old = enumValue;
        this.enumValue = value;
        callCallbacks(value, old);
    }
    public E getEnumValue() {
        return this.enumValue;
    }

    @Override
    public void reset() {
        setEnumValue(defaultValue);
    }

    @Override
    public JsonObject writeConfig() {
        JsonObject object = new JsonObject();
        object.addProperty("value", enumValue.name());
        return object;
    }

    @Override
    public void readConfig(JsonObject in) {
        enumValue = (E) Enum.valueOf(enumValue.getClass(), in.get("value").getAsString());
    }
}
