package net.justacoder.shadowclient.main.ui.clickgui;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.config.ShadowClientSettings;
import net.justacoder.shadowclient.main.render.UIRenderUtils;
import net.justacoder.shadowclient.main.ui.ShadowClientScreen;
import net.justacoder.shadowclient.main.util.JavaUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.justacoder.shadowclient.main.ui.clickgui.text.FrameTextField;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ClickGUI extends Screen implements ShadowClientScreen {

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
    public void onDisplayed() {
        super.onDisplayed();
        frames.forEach(frame -> {
            frame.setOpens(frame.extended);
            frame.setAnimProgress(0); // .startAnimation would set it to 1 for non-extended ones, but we don't want any animation for them
        });
    }

    @Override
    protected void applyBlur() {
        if (ShadowClientSettings.BlurBackground.booleanValue()) {
            super.applyBlur();
        }
    }

    @Override
    public void render(DrawContext context, int scaledMouseX, int scaledMouseY, float delta) {

        this.applyBlur();

        float disableScaleFactor = UIRenderUtils.guiScaleFactor(); // technically we disable, but we use enable() because we need to multiply the coordinates instead of downscaling
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        UIRenderUtils.beforeUIRender(context);

        for (Frame frame : frames) {
            frame.render(context, mouseX, mouseY, delta);
            frame.updatePosition(mouseX, mouseY);
        }
        for (Frame frame : frames) {
            frame.renderDescriptions(context, mouseX, mouseY, delta);
        }

        UIRenderUtils.afterUIRender(context);

    }

    @Override
    public boolean mouseClicked(double scaledMouseX, double scaledMouseY, int button) {

        float disableScaleFactor = UIRenderUtils.guiScaleFactor(); // see render() for reason of using enable...()
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        for (Frame frame : frames) {
            frame.mouseClicked(mouseX, mouseY, button);
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double scaledMouseX, double scaledMouseY, int button) {

        float disableScaleFactor = UIRenderUtils.guiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

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
    public boolean shouldCloseOnEsc() {
        return !isAnyTextFieldCapturing() && (this instanceof MainClickGUI);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        if (!isAnyTextFieldCapturing() && (this instanceof MainClickGUI)) {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }

        if (keyCode == GLFW.GLFW_KEY_ESCAPE && !isAnyTextFieldCapturing() && !(this instanceof MainClickGUI)) {
            client.setScreen(ShadowClientMain.clickGui);
        }

        boolean k = super.keyPressed(keyCode, scanCode, modifiers);

        for (Frame frame : frames) {
            frame.keyPressed(keyCode, scanCode, modifiers);
        }

        searching = !((FrameTextField) searchFrame.children.getFirst()).getText().isEmpty();

        if (searching) {
            searchingFor = ((FrameTextField) searchFrame.children.getFirst()).getText();
        }

        return k;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        for (Frame frame : frames) {
            frame.charTyped(chr, modifiers);
        }
        return super.charTyped(chr, modifiers);
    }

    public List<FrameChild> getAllModuleTextFields() {
        List<FrameChild> textFields = new ArrayList<>();
        for (Frame frame : frames) {
            textFields.addAll(frame.getAllTextFields());
        }
        return textFields;
    }

    public boolean isAnyTextFieldCapturing() {
        List<FrameChild> allTextFields = getAllModuleTextFields();
        for (FrameChild textField : allTextFields) {
            if (textField.getClass() == FrameTextField.class && ((FrameTextField) textField).interceptsKeypresses()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean capturesKeypress(int key) {
        return key == GLFW.GLFW_KEY_ESCAPE || isAnyTextFieldCapturing();
    }
}
