package net.justacoder.shadowclient.main.ui.clickgui.text;

import net.justacoder.shadowclient.main.ui.font.SCFont;
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
        int textOffset = (int) ((float) getParentFrame().height / 2 - SCFont.getHeight() / 2);
        SCFont.renderString(context, text.isEmpty() ? placeholder : text.toLowerCase(), getParentFrame().x + textOffset, getParentFrame().y + offset + textOffset, text.isEmpty() ? Colors.TEXT_DISABLED.color : Colors.TEXT_NORMAL.color);
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        captureKeyPresses = isHovered(mouseX, mouseY);
    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (captureKeyPresses) {
            if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
                if (!text.isEmpty()) {
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


    @Override
    public int getHeight() {
        return getParentFrame().height;
    }

}
