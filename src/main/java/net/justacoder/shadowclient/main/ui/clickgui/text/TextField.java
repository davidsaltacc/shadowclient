package net.justacoder.shadowclient.main.ui.clickgui.text;

import net.minecraft.client.gui.DrawContext;
import net.justacoder.shadowclient.main.ui.clickgui.Colors;
import net.justacoder.shadowclient.main.ui.clickgui.Frame;
import net.justacoder.shadowclient.main.ui.clickgui.FrameChild;
import org.lwjgl.glfw.GLFW;

public class TextField extends FrameChild {

    private final Frame frameParent;
    private String text;
    private final String placeholder;

    public int offset;
    public boolean captureKeyPresses;

    public TextField(Frame parent, int offset, String placeholder) {
        this.frameParent = parent;
        this.offset = offset;
        this.text = "";
        this.placeholder = placeholder;
        captureKeyPresses = false;
    }

    public Frame getParentFrame() {
        return frameParent;
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > getParentFrame().x && mouseX < getParentFrame().x + getParentFrame().width && mouseY > getParentFrame().y + offset && mouseY < getParentFrame().y + offset + getParentFrame().height;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (isHovered(mouseX, mouseY)) {
            context.fill(getParentFrame().x, getParentFrame().y + offset, getParentFrame().x + getParentFrame().width, getParentFrame().y + offset + getParentFrame().height, Colors.MODULE_BUTTON_HOVERED.color);
        } else {
            context.fill(getParentFrame().x, getParentFrame().y + offset, getParentFrame().x + getParentFrame().width, getParentFrame().y + offset + getParentFrame().height, Colors.MODULE_BUTTON_NORMAL.color);
        }
        int textOffset = (getParentFrame().height / 2 - getParentFrame().mc.textRenderer.fontHeight / 2);
        context.drawTextWithShadow(getParentFrame().mc.textRenderer, text.length() == 0 ? placeholder : text.toLowerCase(), getParentFrame().x + textOffset, getParentFrame().y + offset + textOffset, text.length() == 0 ? Colors.TEXT_DISABLED.color : Colors.TEXT_NORMAL.color);
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        captureKeyPresses = isHovered(mouseX, mouseY);
    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (captureKeyPresses) {
            if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
                if (text.length() > 0) {
                    text = text.substring(0, text.length() - 1);
                }
                return;
            }
            if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT || keyCode == GLFW.GLFW_KEY_ESCAPE) {
                captureKeyPresses = false;
                return;
            }
            text += (char) keyCode;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
