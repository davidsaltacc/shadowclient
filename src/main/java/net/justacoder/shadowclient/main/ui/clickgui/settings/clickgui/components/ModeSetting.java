package net.justacoder.shadowclient.main.ui.clickgui.settings.clickgui.components;

import net.justacoder.shadowclient.main.ui.font.SCFont;
import net.minecraft.client.gui.DrawContext;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.setting.settings.EnumSetting;
import net.justacoder.shadowclient.main.ui.clickgui.Colors;
import net.justacoder.shadowclient.main.ui.clickgui.ModuleButton;
import net.justacoder.shadowclient.main.ui.clickgui.settings.clickgui.SettingComponent;
import org.lwjgl.glfw.GLFW;

public class ModeSetting extends SettingComponent {

    private final EnumSetting enumSetting;
    private int enumSettingIndex;

    public ModeSetting(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.enumSetting = (EnumSetting) setting;
        this.enumSettingIndex = 0;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (isHovered(mouseX, mouseY)) {
            context.fill(parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Colors.SETTING_COMPONENT_HOVERED.color);
        } else {
            context.fill(parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Colors.SETTING_COMPONENT_NORMAL.color);
        }
        int textOffset = (int) ((float) parent.parent.height / 2 - SCFont.getHeight() / 2);

        SCFont.renderString(context, enumSetting.name + ": " + enumSetting.getEnumValue().toString(), parent.parent.x + textOffset, parent.parent.y + parent.offset + offset + textOffset, Colors.TEXT_NORMAL.color);

        super.render(context, mouseX, mouseY, delta);
    }


    @SuppressWarnings("unchecked")
    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {

        if (isHovered(mouseX, mouseY) && button == GLFW.GLFW_MOUSE_BUTTON_1) {

            if (enumSetting.getEnumValue().getClass().getEnumConstants()[enumSettingIndex] == enumSetting.getEnumValue()) {
                enumSettingIndex += 1;
            }

            enumSetting.setEnumValue(enumSetting.getEnumValue().getClass().getEnumConstants()[enumSettingIndex]);
            enumSettingIndex += 1;

            if (enumSettingIndex == enumSetting.getEnumValue().getClass().getEnumConstants().length) {
                enumSettingIndex = 0;
            }

        }

        super.mouseClicked(mouseX, mouseY, button);
    }
}
