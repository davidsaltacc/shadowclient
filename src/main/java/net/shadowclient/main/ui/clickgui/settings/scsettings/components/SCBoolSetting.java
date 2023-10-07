package net.shadowclient.main.ui.clickgui.settings.scsettings.components;

import net.minecraft.client.gui.DrawContext;
import net.shadowclient.main.setting.Setting;
import net.shadowclient.main.setting.settings.BooleanSetting;
import net.shadowclient.main.ui.clickgui.Colors;
import net.shadowclient.main.ui.clickgui.Frame;
import net.shadowclient.main.ui.clickgui.settings.scsettings.SCSettingComponent;
import org.lwjgl.glfw.GLFW;

public class SCBoolSetting extends SCSettingComponent {

    private final BooleanSetting booleanSetting;
    private final int offset;

    public SCBoolSetting(Setting setting, Frame parent, int offset) {
        super(setting, parent, offset);
        this.booleanSetting = (BooleanSetting) setting;
        this.offset = offset;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (isHovered(mouseX, mouseY)) {
            context.fill(parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, Colors.SETTING_COMPONENT_HOVERED.color);
        } else {
            context.fill(parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, Colors.SETTING_COMPONENT_NORMAL.color);
        }
        int textOffset = (parent.height / 2 - mc.textRenderer.fontHeight / 2);

        context.drawTextWithShadow(mc.textRenderer, booleanSetting.name + ": " + booleanSetting.booleanValue(), parent.x + textOffset, parent.y + offset + textOffset, getTextColor());

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == GLFW.GLFW_MOUSE_BUTTON_1) {
            booleanSetting.setBooleanValue(!booleanSetting.booleanValue());
        }

        super.mouseClicked(mouseX, mouseY, button);
    }
}
