package net.shadowclient.main.module.modules.render;

import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.NumberSetting;

@ReceiveNoUpdates
@SearchTags({"no fire overlay", "no fire", "no self fire", "no burning", "no fire texture"})
public class NoFireOverlay extends Module {

    public final NumberSetting OFFSET = new NumberSetting("Offset", 0.01f, 0.6f, 0.6f, 0.01f);

    public NoFireOverlay() {
        super("nofireoverlay", "No Fire Overlay", ModuleCategory.RENDER);

        addSetting(OFFSET);
    }

    public float getOffset() {
        return this.enabled ? OFFSET.floatValue() : 0f;
    }

}
