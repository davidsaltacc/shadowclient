package net.justacoder.shadowclient.main.setting.settings;

import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.mixin.KeyBindingAccessor;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class KeySetting extends Setting {

    private static int id = 0;

    private KeyBinding keyBinding;

    public KeySetting(String name, KeyBinding keyBinding) {
        super(name);
        this.keyBinding = keyBinding;
    }

    public KeySetting(String name, int defaultKey) {
        super(name);
        this.keyBinding = new KeyBinding("key.shadowclient.keybind_setting." + id + "_" + name, defaultKey, "category.shadowclient.clientcategory");
        id += 1;
    }

    public int keyValue() {
        return ((KeyBindingAccessor) keyBinding).getBoundKey().getCode();
    }

    public void setKeyValue(int key) {
        int old = this.keyValue();
        this.keyBinding.setBoundKey(InputUtil.Type.KEYSYM.createFromCode(key));
        KeyBinding.updateKeysByCode();
        callCallbacks(this.keyValue(), old);
    }

}
