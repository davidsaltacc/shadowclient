package net.shadowclient.main.ui.clickgui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.shadowclient.main.annotations.Hidden;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.module.ModuleManager;
import net.shadowclient.main.ui.clickgui.settings.clickgui.SettingComponent;
import net.shadowclient.main.ui.clickgui.settings.clickgui.components.TextSetting;
import net.shadowclient.main.ui.clickgui.text.TextField;
import net.shadowclient.main.util.ColorUtils;
import org.lwjgl.glfw.GLFW;
import java.util.ArrayList;
import java.util.List;

public class Frame extends FrameChild {

    public int x;
    public int y;
    public final int width;
    public final int height;
    public int dragX;
    public int dragY;
    public final String name;
    public boolean dragging;
    public boolean extended;

    public final MinecraftClient mc = MinecraftClient.getInstance();

    public final List<FrameChild> children;



    public Frame(ModuleCategory category, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = category.name;
        this.dragging = false;
        this.extended = true;

        children = new ArrayList<>();

        int offset = height;
        for (String modulename : ModuleManager.getAllModuleNamesInCategory(category)) {
            if (ModuleManager.getModule(modulename).getClass().isAnnotationPresent(Hidden.class)) {
                continue;
            }
            ModuleButton button = new ModuleButton(modulename, this, offset);
            children.add(button);
            ModuleManager.getModule(modulename).moduleButton = button;
            offset += height;
        }

    }

    public Frame(String name, int x, int y, int width, int height) { // search
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragging = false;
        this.extended = true;
        this.name = name;

        children = new ArrayList<>();

    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int textOffset = (height / 2 - mc.textRenderer.fontHeight / 2);

        int rainbowcolor = 0;
        if (ModuleManager.RainbowGUIModule.enabled) {
            float[] rainbowF = ColorUtils.rainbowColor();
            int[] rainbowI = ColorUtils.RGBFloatToRGBInt(rainbowF[0], rainbowF[1], rainbowF[2]);
            rainbowcolor = ColorUtils.RGBA2int(rainbowI[0], rainbowI[1], rainbowI[2], 255);
        }

        context.fill(x, y, x + width, y + height, ModuleManager.RainbowGUIModule.enabled ? rainbowcolor : Colors.CATEGORY_FRAME.color);
        context.drawTextWithShadow(mc.textRenderer, name, x + textOffset, y + textOffset, Colors.TEXT_NORMAL.color);
        context.drawTextWithShadow(mc.textRenderer, extended ? "-" : "+", x + width - textOffset - mc.textRenderer.getWidth("+"), y + textOffset, Colors.TEXT_NORMAL.color);


        if (extended) {
            for (FrameChild child : children) {
                child.render(context, mouseX, mouseY, delta);
            }
            for (FrameChild child : children) {
                if (child instanceof ModuleButton) {
                    if (((ModuleButton) child).isHovered(mouseX, mouseY)) {
                        ((ModuleButton) child).renderDescription(context, mouseX, mouseY);
                    }
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
            if (child.getClass().equals(ModuleButton.class)) {
                if (((ModuleButton) child).extended) {
                    for (SettingComponent ignored : ((ModuleButton) child).components) {
                        offset += height;
                    }
                }
            }
        }
    }

    public List<FrameChild> getAllTextFields() {
        List<FrameChild> textFields = new ArrayList<>();
        for (FrameChild child : children) {
            if (child.getClass() == TextField.class) {
                textFields.add(child);
            }
            if (child.getClass() == ModuleButton.class) {
                ((ModuleButton) child).components.forEach((component) -> {
                    if (component.getClass() == TextSetting.class) {
                        textFields.add(component);
                    }
                });
            }
        }
        return textFields;
    }
}
