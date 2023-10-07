package net.shadowclient.main.ui.clickgui.settings.scsettings;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.shadowclient.main.SCMain;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.config.ConfigFiles;
import net.shadowclient.main.setting.Setting;
import net.shadowclient.main.ui.clickgui.Colors;
import net.shadowclient.main.ui.clickgui.Frame;
import net.shadowclient.main.ui.clickgui.FrameChild;

public class SCSettingComponent extends FrameChild {

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
        return setting.name.toLowerCase().contains(SCMain.settingsGui.searchingFor.toLowerCase());
    }

    public int getTextColor() {
        if (SCMain.clickGui.searching && !isGettingSearchedFor()) {
            return Colors.TEXT_DISABLED.color;
        }
        return Colors.TEXT_NORMAL.color;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            ConfigFiles.saveConfig();
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {

    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {

    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.x && mouseX < parent.x + parent.width && mouseY > parent.y + offset && mouseY < parent.y + offset + parent.height;
    }
}
