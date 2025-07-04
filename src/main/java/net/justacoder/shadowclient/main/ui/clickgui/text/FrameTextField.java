package net.justacoder.shadowclient.main.ui.clickgui.text;

import net.justacoder.shadowclient.main.render.UIRenderUtils;
import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.justacoder.shadowclient.main.ui.text.TextField;
import net.justacoder.shadowclient.main.util.JavaUtils;
import net.minecraft.client.gui.DrawContext;
import net.justacoder.shadowclient.main.ui.clickgui.Frame;
import net.justacoder.shadowclient.main.ui.clickgui.FrameChild;
import org.joml.Vector2f;

public class FrameTextField extends FrameChild {

    private final Frame frameParent;
    private TextField textField;
    private int offset;

    public FrameTextField(Frame parent, int offset, TranslatableString placeholder) {
        this.frameParent = parent;
        this.offset = offset;
        textField = new TextField(parent.getScreen(), "", placeholder, new Vector2f(getParentFrame().x, getParentFrame().y + offset), new Vector2f(getParentFrame().width, getParentFrame().height), text -> {
            // TODO use this properly
        });
    }

    public Frame getParentFrame() {
        return frameParent;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (textField.getPosition().x != getParentFrame().x || textField.getPosition().y != getParentFrame().y + offset) {
            textField.setPosition(new Vector2f(getParentFrame().x, getParentFrame().y + offset));
        }
        textField.render(context);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        textField.mouseClicked(mouseX / UIRenderUtils.guiScaleFactor(), mouseY / UIRenderUtils.guiScaleFactor(), button);
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        textField.keyPressed(keyCode, scanCode, modifiers);
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

    @Override
    public int getHeight() {
        return getParentFrame().height;
    }

    public boolean interceptsKeypresses() {
        return textField.capturesKeypress();
    }

    @Override
    public void charTyped(char c, int mod) {
        textField.charTyped(c, mod);
    }

}
