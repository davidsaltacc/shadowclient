package net.justacoder.shadowclient.main.ui.clickgui;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.ui.clickgui.text.TextField;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainClickGUI extends ClickGUI {

    public final MinecraftClient mc = MinecraftClient.getInstance();

    public MainClickGUI() {
        super("ClickGUI");

        for (ModuleCategory category : ModuleCategory.values()) {
            if (category.hiddenFromMain) {
                continue;
            }
            frames.add(Frame.create(category, 0, 0, 200, 26));
        }

        searchFrame = Frame.createWithoutAddingModules(ModuleCategory.SEARCH, 0, 0, 200, 26);
        frames.add(searchFrame);
        searchFrame.children.add(new TextField(searchFrame, 24, "textfield.placeholder.find_module"));
    }

    public void repositionFramesProperly() {

        int width = mc.getWindow().getMonitor().findClosestVideoMode(mc.getWindow().getFullscreenVideoMode()).getWidth(); // why is this so hard???? anyway it works now

        int columns = (int) Math.floor((float) (width / 2.) / 210);

        int[] columnsY = new int[columns];
        Arrays.fill(columnsY, 10);

        for (int index = 0; index < frames.size(); index++) {
            int xCol = index % columns;
            Frame frame = frames.get(index);
            if (columnsY[xCol] == 10) {
                frame.y = columnsY[xCol];
                columnsY[xCol] += frame.getHeight() + 10;
                frame.x = 10 + xCol * 210;
            } else {
                int minY = (int) 1e7;
                int minYIndex = -1;
                for (int ind = 0; ind < columns; ind++) {
                    int newY = columnsY[ind] + frame.getHeight();
                    if (newY < minY) {
                        minY = newY;
                        minYIndex = ind;
                    }
                }
                frame.y = columnsY[minYIndex];
                columnsY[minYIndex] += frame.getHeight() + 10;
                frame.x = 10 + minYIndex * 210;
            }
        }
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
            if (ShadowClientMain.toggleGUIKeyBinding.matchesKey(keyCode, scanCode)) {
                ModuleManager.endKeybindConfiguration();
                ModuleManager.ConfigureKeybindingsModule.setDisabled();
                ShadowClientMain.mc.setScreen(null);
                return true;
            }
            if (ModuleManager.getConfiguringKeyBinding() != null) {
                int code = keyCode == GLFW.GLFW_KEY_ESCAPE ? GLFW.GLFW_KEY_UNKNOWN : keyCode;
                ModuleManager.getConfiguringKeyBinding().setBoundKey(InputUtil.Type.KEYSYM.createFromCode(code));
                ModuleManager.getConfiguringKeyBindingModule().reloadKeybindTranslation();
                KeyBinding.updateKeysByCode();
                ModuleManager.setConfiguringKeyBinding(null, null);
            }
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
        }
        return false;
    }
}
