package net.justacoder.shadowclient.main.ui.clickgui.settings.clickgui;

import net.minecraft.client.MinecraftClient;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.ui.clickgui.FrameChild;
import net.justacoder.shadowclient.main.ui.clickgui.ModuleButton;

public class SettingComponent extends FrameChild {

    public final Setting setting;
    public final ModuleButton parent;
    public final int offset;

    protected final MinecraftClient mc = MinecraftClient.getInstance();

    public SettingComponent(Setting setting, ModuleButton parent, int offset) {
        this.setting = setting;
        this.parent = parent;
        this.offset = offset;
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.parent.x && mouseX < parent.parent.x + parent.parent.width && mouseY > parent.parent.y + parent.offset + offset && mouseY < parent.parent.y + parent.offset + offset + parent.parent.height;
    }
}
