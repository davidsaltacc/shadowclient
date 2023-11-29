package net.shadowclient.main.ui.clickgui.settings.clickgui.components;

import net.minecraft.client.gui.DrawContext;
import net.shadowclient.main.setting.Setting;
import net.shadowclient.main.setting.settings.BooleanSetting;
import net.shadowclient.main.ui.clickgui.Colors;
import net.shadowclient.main.ui.clickgui.ModuleButton;
import net.shadowclient.main.ui.clickgui.settings.clickgui.SettingComponent;
import org.lwjgl.glfw.GLFW;

public class BoolSetting extends SettingComponent {

    private final BooleanSetting booleanSetting;
    private final int offset;

    public BoolSetting(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.booleanSetting = (BooleanSetting) setting;
        this.offset = offset;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) { // todo render as checkbox
        if (isHovered(mouseX, mouseY)) {
            context.fill(parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Colors.SETTING_COMPONENT_HOVERED.color);
        } else {
            context.fill(parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Colors.SETTING_COMPONENT_NORMAL.color);
        }
        int textOffset = (parent.parent.height / 2 - mc.textRenderer.fontHeight / 2);

        context.drawTextWithShadow(mc.textRenderer, booleanSetting.name + ": " + (booleanSetting.booleanValue() ? "yes" : "no"), parent.parent.x + textOffset, parent.parent.y + parent.offset + offset + textOffset, Colors.TEXT_NORMAL.color);

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
