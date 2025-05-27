package net.justacoder.shadowclient.main.ui.clickgui;

import net.justacoder.shadowclient.main.ui.font.Font;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.justacoder.shadowclient.main.annotations.Hidden;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.ui.clickgui.text.TextField;
import net.justacoder.shadowclient.main.util.ColorUtils;
import org.lwjgl.glfw.GLFW;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Frame extends FrameChild {

    public int x;
    public int y;
    public final int width;
    public final int height;
    public int dragX;
    public int dragY;
    public String name;
    public boolean dragging;
    public boolean extended;

    public ModuleCategory category;

    public final MinecraftClient mc = MinecraftClient.getInstance();

    public final List<FrameChild> children;

    public static final List<Frame> allFrames = new ArrayList<>();

    private Frame(ModuleCategory category, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.category = category;
        reloadTranslation();
        this.dragging = false;
        this.extended = true;

        children = new ArrayList<>();

        int offset = height;
        for (String modulename : ModuleManager.getAllModuleNamesInCategory(category)) {
            if (ModuleManager.getModule(modulename).getClass().isAnnotationPresent(Hidden.class) ) {
                continue;
            }
            ModuleButton button = new ModuleButton(modulename, this, offset);
            children.add(button);
            ModuleManager.getModule(modulename).moduleButton = button;
            offset += height;
        }

        allFrames.add(this);

    }

    public void reloadTranslation() {
        this.name = category.friendlyName;
    }

    private Frame(ModuleCategory category, int x, int y, int width, int height, boolean __) { // search
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragging = false;
        this.extended = true;
        this.category = category;
        reloadTranslation();

        children = new ArrayList<>();

        allFrames.add(this);
    }

    public static Frame create(ModuleCategory category, int x, int y, int width, int height) {
        return new Frame(category, x, y, width, height);
    }

    public static Frame createWithoutAddingModules(ModuleCategory category, int x, int y, int width, int height) {
        return new Frame(category, x, y, width, height, false);
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int textOffset = (int) ((float) height / 2 - (float) Font.getHeight() / 2);

        if (ModuleManager.RainbowGUIModule.enabled) {
            float[] rainbowF = ColorUtils.rainbowColor();
            int[] rainbowI = ColorUtils.RGBFloatToRGBInt(rainbowF[0], rainbowF[1], rainbowF[2]);
            int rainbowcolor = ColorUtils.RGBA2int(rainbowI[0], rainbowI[1], rainbowI[2], 255);
            context.fill(x, y, x + width, y + height, rainbowcolor);
        } else {
            int[] colorArray = ColorUtils.int2RGBA(Colors.CATEGORY_FRAME.color);
            int colorLighter = ColorUtils.RGBA2int(colorArray[0] + 25, colorArray[1] + 25, colorArray[2] + 25, 255);
            context.fillGradient(x, y, x + width, y + height, Colors.CATEGORY_FRAME.color, colorLighter);
        }

        Font.renderString(context, name, x + textOffset, y + textOffset, Colors.TEXT_NORMAL.color);
        Font.renderString(context, extended ? "-" : "+", x + width - textOffset - (float) Font.getWidth("+"), y + textOffset, Colors.TEXT_NORMAL.color);


        if (extended) {
            for (FrameChild child : children) {
                child.render(context, mouseX, mouseY, delta);
            }
        }
    }

    public void renderDescriptions(DrawContext context, int mouseX, int mouseY, float delta) {
        if (extended) {
            for (FrameChild child : children) {
                if (child instanceof ModuleButton button && button.isHovered(mouseX, mouseY)) {
                        button.renderDescription(context, mouseX, mouseY);
                }
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
                dragging = true;
                dragX = (int) (mouseX - x);
                dragY = (int) (mouseY - y);
            } else if (button == GLFW.GLFW_MOUSE_BUTTON_2) {
                extended = !extended;
            }
        }

        if (extended) {
            for (FrameChild child : children) {
                child.mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (dragging && button == GLFW.GLFW_MOUSE_BUTTON_1) {
            dragging = false;
        }

        if (extended) {
            for (FrameChild child : children) {
                child.mouseReleased(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (extended) {
            for (FrameChild child : children) {
                child.keyPressed(keyCode, scanCode, modifiers);
            }
        }
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public void updatePosition(double mouseX, double mouseY) {
        if (dragging) {
            x = (int) (mouseX - dragX);
            y = (int) (mouseY - dragY);
        }
    }

    public void updateButtons() {
        int offset = height;
        for (FrameChild child : children) {

            if (child.getClass().equals(ModuleButton.class)) {
                ((ModuleButton) child).offset = offset;
            } else if (child.getClass().equals(TextField.class)) {
                ((TextField) child).offset = offset;
            }
            offset += height;
        }
    }

    public List<FrameChild> getAllTextFields() {
        List<FrameChild> textFields = new ArrayList<>();
        for (FrameChild child : children) {
            if (child.getClass() == TextField.class) {
                textFields.add(child);
            }
        }
        return textFields;
    }

    public int getHeight() {
        AtomicInteger height = new AtomicInteger(this.height);
        if (extended) {
            children.forEach(child -> height.set(height.get() + child.getHeight()));
        }
        return height.get();
    }
}
