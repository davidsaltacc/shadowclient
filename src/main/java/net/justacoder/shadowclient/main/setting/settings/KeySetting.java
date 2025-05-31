package net.justacoder.shadowclient.main.setting.settings;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.mixin.KeyBindingAccessor;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class KeySetting extends Setting {

    private final KeyBinding keyBinding;

    public KeySetting(String name, KeyBinding keyBinding) {
        super(name);
        this.keyBinding = keyBinding;
    }

    public int keyValue() {
        return ((KeyBindingAccessor) keyBinding).getBoundKey().getCode();
    }

    public void setKeyValue(int key) {
        int old = this.keyValue();
        this.keyBinding.setBoundKey(InputUtil.Type.KEYSYM.createFromCode(key));
        KeyBinding.updateKeysByCode();
        ShadowClientMain.mc.options.write();
        callCallbacks(this.keyValue(), old);
    }

}
