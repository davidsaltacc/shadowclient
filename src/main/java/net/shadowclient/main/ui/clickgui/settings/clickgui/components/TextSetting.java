package net.shadowclient.main.ui.clickgui.settings.clickgui.components;

import net.minecraft.client.gui.DrawContext;
import net.shadowclient.main.setting.Setting;
import net.shadowclient.main.setting.settings.StringSetting;
import net.shadowclient.main.ui.clickgui.Colors;
import net.shadowclient.main.ui.clickgui.ModuleButton;
import net.shadowclient.main.ui.clickgui.settings.clickgui.SettingComponent;
import org.lwjgl.glfw.GLFW;

public class TextSetting extends SettingComponent {

    private final StringSetting stringSetting;
    private final int offset;
    private final String placeholder;

    public boolean captureKeyPresses;

    public TextSetting(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.stringSetting = (StringSetting) setting;
        this.offset = offset;
        this.placeholder = setting.name;
        this.captureKeyPresses = false;
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.parent.x && mouseX < parent.parent.x + parent.parent.width && mouseY > parent.parent.y + offset && mouseY < parent.parent.y + offset + parent.parent.height;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (isHovered(mouseX, mouseY)) {
            context.fill(parent.parent.x, parent.parent.y + offset, parent.parent.x + parent.parent.width, parent.parent.y + offset + parent.parent.height, Colors.MODULE_BUTTON_HOVERED.color);
        } else {
            context.fill(parent.parent.x, parent.parent.y + offset, parent.parent.x + parent.parent.width, parent.parent.y + offset + parent.parent.height, Colors.MODULE_BUTTON_NORMAL.color);
        }
        int textOffset = (parent.parent.height / 2 - parent.parent.mc.textRenderer.fontHeight / 2);
        context.drawTextWithShadow(mc.textRenderer, stringSetting.stringValue().length() == 0 ? placeholder :  stringSetting.stringValue().toLowerCase(), parent.parent.x + textOffset, parent.parent.y + offset + textOffset, stringSetting.stringValue().length() == 0 ? Colors.TEXT_DISABLED.color : Colors.TEXT_NORMAL.color);
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        captureKeyPresses = isHovered(mouseX, mouseY);
    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (captureKeyPresses) {
            if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
                if (stringSetting.stringValue().length() > 0) {
                    stringSetting.setStringValue(stringSetting.stringValue().substring(0, stringSetting.stringValue().length() - 1));
                }
                return;
            }
            if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT) {
                captureKeyPresses = false;
                return;
            }
            stringSetting.setStringValue(stringSetting.stringValue() + (char) keyCode);
        }
    }
}
