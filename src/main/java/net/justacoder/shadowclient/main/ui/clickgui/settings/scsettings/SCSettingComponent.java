package net.justacoder.shadowclient.main.ui.clickgui.settings.scsettings;

import net.minecraft.client.MinecraftClient;
import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.ui.Colors;
import net.justacoder.shadowclient.main.ui.clickgui.Frame;
import net.justacoder.shadowclient.main.ui.clickgui.FrameChild;

public abstract class SCSettingComponent extends FrameChild {

    public final Setting setting;
    public final Frame parent;
    public final int offset;

    protected final MinecraftClient mc = MinecraftClient.getInstance();

    public SCSettingComponent(Setting setting, Frame parent, int offset) {
        this.setting = setting;
        this.parent = parent;
        this.offset = offset;
    }

    public boolean isGettingSearchedFor() {
        return setting.name.getTranslation().toLowerCase().contains(ShadowClientMain.settingsGui.searchingFor.toLowerCase());
    }

    public int getTextColor() {
        if (ShadowClientMain.settingsGui.searching && !isGettingSearchedFor()) {
            return Colors.TEXT_DISABLED.color;
        }
        return Colors.TEXT_NORMAL.color;
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.x && mouseX < parent.x + parent.width && mouseY > parent.y + offset && mouseY < parent.y + offset + parent.height;
    }
}
