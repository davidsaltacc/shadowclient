package net.shadowclient.main.ui.clickgui.settings.clickgui.components;

import net.minecraft.client.gui.DrawContext;
import net.shadowclient.main.setting.Setting;
import net.shadowclient.main.setting.settings.EnumSetting;
import net.shadowclient.main.ui.clickgui.Colors;
import net.shadowclient.main.ui.clickgui.ModuleButton;
import net.shadowclient.main.ui.clickgui.settings.clickgui.SettingComponent;
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
        int textOffset = (parent.parent.height / 2 - mc.textRenderer.fontHeight / 2);

        context.drawTextWithShadow(mc.textRenderer, enumSetting.name + ": " + enumSetting.getEnumValue().toString(), parent.parent.x + textOffset, parent.parent.y + parent.offset + offset + textOffset, Colors.TEXT_NORMAL.color);

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
