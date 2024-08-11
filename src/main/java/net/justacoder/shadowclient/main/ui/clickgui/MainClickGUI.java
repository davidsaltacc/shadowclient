package net.justacoder.shadowclient.main.ui.clickgui;

import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.config.Config;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.ui.clickgui.settings.clickgui.components.TextSetting;
import net.justacoder.shadowclient.main.ui.clickgui.text.TextField;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainClickGUI extends ClickGUI {

    public final List<Frame> frames;

    public Frame searchFrame;
    public boolean searching;
    public String searchingFor;

    public final MinecraftClient mc = MinecraftClient.getInstance();

    public MainClickGUI() {
        super("ClickGUI");

        frames = new ArrayList<>();
        searching = false;
        searchingFor = "";

        int offset = 5;
        for (ModuleCategory category : ModuleCategory.values()) {
            frames.add(new Frame(category, offset, 5, 100, 13));
            offset += 105;
        }

        searchFrame = new Frame("Search", offset, 5, 100, 12);
        frames.add(searchFrame);
        searchFrame.children.add(new TextField(searchFrame, 12, "Find Module"));
    }

    public void repositionFramesProperly() {

        if (Config.configLoaded) {
            return;
        }

        int screenWidth = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()).width() / mc.options.getGuiScale().getValue();
        int columns = (int) Math.floor((float) screenWidth / 105);

        int[] columnsY = new int[columns];
        Arrays.fill(columnsY, 5);

        for (int index = 0; index < frames.size(); index++) {
            int xCol = index % columns;
            Frame frame = frames.get(index);
            frame.y = columnsY[xCol];
            columnsY[xCol] += frame.getHeight() + 5;
            frame.x = 5 + xCol * 105;
        }
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
    public boolean shouldCloseOnEsc() {
        if (ModuleManager.isConfiguringKeyBinds()) {
            return false;
        }
        return super.shouldCloseOnEsc();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        if (ModuleManager.isConfiguringKeyBinds()) {
            if (SCMain.ToggleGUIKeyBinding.matchesKey(keyCode, scanCode)) {
                ModuleManager.endKeybindConfiguration();
                ModuleManager.ConfigureKeybindingsModule.setDisabled();
                SCMain.mc.setScreen(null);
                return true;
            }
            if (ModuleManager.getConfiguringKeyBinding() != null) {
                int code = keyCode == GLFW.GLFW_KEY_ESCAPE ? GLFW.GLFW_KEY_UNKNOWN : keyCode;
                ModuleManager.getConfiguringKeyBinding().setBoundKey(InputUtil.Type.KEYSYM.createFromCode(code));
                ModuleManager.getConfiguringKeyBindingModule().reloadKeybindTranslation();
                KeyBinding.updateKeysByCode();
                ModuleManager.setConfiguringKeyBinding(null, null);
            }
            return super.keyPressed(keyCode, scanCode, modifiers);
        }

        searching = !((TextField) searchFrame.children.get(0)).getText().isEmpty();

        if (searching) {
            searchingFor = ((TextField) searchFrame.children.get(0)).getText();
        }

        for (Frame frame : frames) {
            frame.keyPressed(keyCode, scanCode, modifiers);
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
