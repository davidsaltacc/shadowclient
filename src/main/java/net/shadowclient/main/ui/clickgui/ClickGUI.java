package net.shadowclient.main.ui.clickgui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.shadowclient.main.config.SCSettings;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.ui.clickgui.settings.scsettings.components.SCBoolSetting;
import net.shadowclient.main.ui.clickgui.text.TextField;
import java.util.ArrayList;
import java.util.List;

public class ClickGUI extends Screen {

    private final List<Frame> frames;

    public final Frame searchFrame;
    public boolean searching;
    public String searchingFor;

    public ClickGUI(boolean main) {
        super(Text.of(main ? "ClickGUI" : "ShadowClient Settings"));
        if (main) {

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
        } else {
            frames = new ArrayList<>();
            searching = false;
            searchingFor = "";

            int offset = 5;

            Frame settingsframe = new Frame("Settings", offset, 5, 100, 14);
            frames.add(settingsframe);
            offset += 105;

            Frame hideframe = new Frame("Options", offset, 5, 100, 14);
            frames.add(hideframe);
            hideframe.children.add(new ModuleButton("hidesettings", hideframe, 14));
            hideframe.children.add(new ModuleButton("loaddata", hideframe, 28));
            hideframe.children.add(new ModuleButton("savedata", hideframe, 42));
            offset += 105;

            settingsframe.children.add(new SCBoolSetting(SCSettings.VanillaSpoof, settingsframe, 14));
            settingsframe.children.add(new SCBoolSetting(SCSettings.WelcomeMessage, settingsframe, 28));

            searchFrame = new Frame("Search", offset, 5, 120, 14);
            frames.add(searchFrame);
            searchFrame.children.add(new TextField(searchFrame, 14, "Find Setting")); // todo fix searching fro settings?  (wont let you type, doesnt even capture/intercept keypresses)
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
