package net.shadowclient.main.ui.clickgui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.shadowclient.main.ui.clickgui.text.TextField;
import java.util.ArrayList;
import java.util.List;

public class ClickGUI extends Screen {

    public final List<Frame> frames;

    public Frame searchFrame;
    public boolean searching;
    public String searchingFor;
    public ClickGUI(String title) {
        super(Text.of(title));

        frames = new ArrayList<>();
        searching = false;
        searchingFor = "";
        searchFrame = null;

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
    public boolean shouldPause() {
        return false;
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

    public List<TextField> getAllTextFields() {
        List<TextField> textFields = new ArrayList<>();
        for (Frame frame : frames) {
            textFields.addAll(frame.getAllTextFields());
        }
        return textFields;
    }

    public boolean isAnyTextFieldCapturing() {
        List<TextField> allTextFields = getAllTextFields();
        for (TextField textField : allTextFields) {
            if (textField.captureKeyPresses) {
                return true;
            }
        }
        return false;
    }
}
