package net.justacoder.shadowclient.main.ui.clickgui;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.config.ShadowClientSettings;
import net.justacoder.shadowclient.main.render.UIRenderUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.justacoder.shadowclient.main.ui.clickgui.settings.clickgui.components.TextSetting;
import net.justacoder.shadowclient.main.ui.clickgui.text.TextField;
import org.lwjgl.glfw.GLFW;

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
    protected void applyBlur() {
        if (ShadowClientSettings.BlurBackground.booleanValue()) {
            super.applyBlur();
        }
    }

    @Override
    public void render(DrawContext context, int scaledMouseX, int scaledMouseY, float delta) {

        this.applyBlur();

        float disableScaleFactor = UIRenderUtils.enableGuiScaleFactor(); // technically we disable, but we use enable() because we need to multiply the coordinates instead of downscaling
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

        float disableScaleFactor = UIRenderUtils.enableGuiScaleFactor(); // see render() for reason of using enable...()
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        for (Frame frame : frames) {
            frame.mouseClicked(mouseX, mouseY, button);
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double scaledMouseX, double scaledMouseY, int button) {

        float disableScaleFactor = UIRenderUtils.enableGuiScaleFactor();
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
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        if (keyCode == GLFW.GLFW_KEY_ESCAPE && !(this instanceof MainClickGUI) && !this.isAnyTextFieldCapturing()) {
            ShadowClientMain.mc.setScreen(ShadowClientMain.clickGui);
        }
        if (ShadowClientMain.toggleGUIKeyBinding.matchesKey(keyCode, scanCode) && !(this instanceof MainClickGUI)) {
            ShadowClientMain.mc.setScreen(null);
            return true;
        }

        for (Frame frame : frames) {
            frame.keyPressed(keyCode, scanCode, modifiers);
        }

        searching = !((TextField) searchFrame.children.getFirst()).getText().isEmpty();

        if (searching) {
            searchingFor = ((TextField) searchFrame.children.getFirst()).getText();
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
