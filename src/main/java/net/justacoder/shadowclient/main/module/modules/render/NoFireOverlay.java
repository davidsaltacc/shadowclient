package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.util.MathUtils;

public class NoFireOverlay extends Module {

    public final NumberSetting OFFSET = new NumberSetting("Offset", 0.01f, 0.6f, 0.6f, 2, MathUtils.Easing.EASE_IN_QUADRATIC);

    public NoFireOverlay() {
        super("nofireoverlay",  ModuleCategory.RENDER, new String[]{"no fire overlay", "no fire", "no self fire", "no burning", "no fire texture"});

        addSetting(OFFSET);
    }

    public float getOffset() {
        return this.enabled ? OFFSET.floatValue() : 0f;
    }

}
