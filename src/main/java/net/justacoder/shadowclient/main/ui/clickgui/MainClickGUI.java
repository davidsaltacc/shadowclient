package net.justacoder.shadowclient.main.ui.clickgui;

import net.minecraft.client.gui.DrawContext;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.ui.clickgui.settings.clickgui.components.TextSetting;
import net.justacoder.shadowclient.main.ui.clickgui.text.TextField;
import java.util.ArrayList;
import java.util.List;

public class MainClickGUI extends ClickGUI {

    public final List<Frame> frames;

    public Frame searchFrame;
    public boolean searching;
    public String searchingFor;

    public MainClickGUI() {
        super("ClickGUI");

        frames = new ArrayList<>();
        searching = false;
        searchingFor = "";

        int offset = 5;
        for (ModuleCategory category : ModuleCategory.values()) {
            frames.add(new Frame(category, offset, 5, 100, 14));
            offset += 105;
        }

        searchFrame = new Frame("Search", offset, 5, 120, 14);
        frames.add(searchFrame);
        searchFrame.children.add(new TextField(searchFrame, 14, "Find Module"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        for (Frame frame : frames) {
            frame.render(context, mouseX, mouseY, delta);
            frame.updatePosition(mouseX, mouseY);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        for (Frame frame : frames) {
            frame.mouseClicked(mouseX, mouseY, button);
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {

        for (Frame frame : frames) {
            frame.mouseReleased(mouseX, mouseY, button);
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        for (Frame frame : frames) {
            frame.keyPressed(keyCode, scanCode, modifiers);
        }

        searching = ((TextField) searchFrame.children.get(0)).getText().length() != 0;

        if (searching) {
            searchingFor = ((TextField) searchFrame.children.get(0)).getText();
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public List<FrameChild> getAllTextFields() {
        List<FrameChild> textFields = new ArrayList<>();
        for (Frame frame : frames) {
            textFields.addAll(frame.getAllTextFields());
        }
        return textFields;
    }

    public boolean isAnyTextFieldCapturing() {
        List<FrameChild> allTextFields = getAllTextFields();
        for (FrameChild textField : allTextFields) {
            if (textField.getClass() == TextField.class) {
                if (((TextField) textField).captureKeyPresses) {
                    return true;
                }
            }
            if (textField.getClass() == TextSetting.class) {
                if (((TextSetting) textField).captureKeyPresses) {
                    return true;
                }
            }
        }
        return false;
    }
}
