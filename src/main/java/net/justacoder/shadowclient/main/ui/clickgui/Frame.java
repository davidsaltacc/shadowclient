package net.justacoder.shadowclient.main.ui.clickgui;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.config.ConfigSaveable;
import net.justacoder.shadowclient.main.config.ShadowClientSettings;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.ui.Colors;
import net.justacoder.shadowclient.main.ui.ShadowClientScreen;
import net.justacoder.shadowclient.main.ui.animation.Animatable;
import net.justacoder.shadowclient.main.ui.font.Font;
import net.justacoder.shadowclient.main.util.MathUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.justacoder.shadowclient.main.annotations.Hidden;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.ui.clickgui.text.FrameTextField;
import net.justacoder.shadowclient.main.util.ColorUtils;
import org.lwjgl.glfw.GLFW;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Frame extends FrameChild implements Animatable, ConfigSaveable {

    public int x;
    public int y;
    public final int width;
    public final int height;
    public int dragX;
    public int dragY;
    public boolean dragging;
    public boolean extended;

    public double animProgress = 1;
    public double animDuration = 0.5;
    public boolean opens = true;

    public ModuleCategory category;

    public final MinecraftClient mc = MinecraftClient.getInstance();

    public final List<FrameChild> children;

    public static final List<Frame> allFrames = new ArrayList<>();

    private final ShadowClientScreen screen;

    private Frame(ShadowClientScreen screen, ModuleCategory category, int x, int y, int width, int height) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.category = category;
        this.dragging = false;
        this.extended = true;

        children = new ArrayList<>();

        int offset = height;
        for (Module module : ModuleManager.getAllModulesInCategory(category)) {
            if (module.getClass().isAnnotationPresent(Hidden.class) ) {
                continue;
            }
            ModuleButton button = new ModuleButton(module, this, offset);
            children.add(button);
            module.moduleButton = button;
            offset += height;
        }

        resortModules();

        allFrames.add(this);

    }

    public void resortModules() {
        List<FrameChild> orderedChildren = new ArrayList<>(children);
        if (ShadowClientSettings.moduleSorting.getEnumValue() == ShadowClientSettings.ModuleSorting.ALPHABETICAL) {
            orderedChildren.sort((c1, c2) -> {
                if (c1 instanceof ModuleButton mb1 && c2 instanceof ModuleButton mb2) {
                    return mb1.module.name.getTranslation().compareTo(mb2.module.name.getTranslation());
                } else {
                    return -(int) 10e7;
                }
            });
        }
        if (ShadowClientSettings.moduleSortingDirection.getEnumValue() == ShadowClientSettings.ModuleSortingDirection.DESCENDING) {
            orderedChildren = orderedChildren.reversed();
        }
        int offset = height;
        for (FrameChild child : orderedChildren) {
            if (child instanceof ModuleButton button) {
                button.offset = offset;
                offset += height;
            }
        }
    }

    private Frame(ShadowClientScreen screen, ModuleCategory category, int x, int y, int width, int height, boolean __) { // search
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragging = false;
        this.extended = true;
        this.category = category;

        children = new ArrayList<>();

        allFrames.add(this);
    }

    public static Frame create(ShadowClientScreen screen, ModuleCategory category, int x, int y, int width, int height) {
        return new Frame(screen, category, x, y, width, height);
    }

    public static Frame createWithoutAddingModules(ShadowClientScreen screen, ModuleCategory category, int x, int y, int width, int height) {
        return new Frame(screen, category, x, y, width, height, false);
    }

    @Override
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

        Font.renderString(context, category.name.getTranslation(), x + textOffset, y + textOffset, Colors.TEXT_NORMAL.color);
        Font.renderString(context, extended ? "-" : "+", x + width - textOffset - (float) Font.getWidth("+"), y + textOffset, Colors.TEXT_NORMAL.color);


        if (extended || animProgress > 0) {
            int totalHeight = 0;
            for (FrameChild child : children) {
                totalHeight += child.getHeight();
            }
            int h = y + height + (int) Math.floor(totalHeight * (opens ? MathUtils.Easing.EASE_OUT_CUBIC : MathUtils.Easing.EASE_IN_CUBIC).eased(animProgress));
            context.enableScissor(x, y + height, x + width, h);
            for (FrameChild child : children) {
                child.render(context, mouseX, mouseY, delta);
            }
            context.disableScissor();
        }

        progressAnimation();

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

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
                dragging = true;
                dragX = (int) (mouseX - x);
                dragY = (int) (mouseY - y);
            } else if (button == GLFW.GLFW_MOUSE_BUTTON_2) {
                extended = !extended;
                setOpens(extended);
                startAnimation();
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

    @Override
    public void charTyped(char c, int mod) {
        if (extended) {
            for (FrameChild child : children) {
                child.charTyped(c, mod);
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

    public List<FrameChild> getAllTextFields() {
        List<FrameChild> textFields = new ArrayList<>();
        for (FrameChild child : children) {
            if (child.getClass() == FrameTextField.class) {
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

    public void setOpens(boolean opens) {
        this.opens = opens;
    }

    @Override
    public void setAnimProgress(double progress) {
        this.animProgress = progress;
    }

    @Override
    public double getAnimProgress() {
        return animProgress;
    }

    @Override
    public double getAnimDuration() {
        return animDuration;
    }

    @Override
    public boolean animProgressesUp() {
        return opens;
    }

    @Override
    public void setAnimProgressesUp(boolean up) {}

    public ShadowClientScreen getScreen() {
        return screen;
    }

    @Override
    public JsonObject writeConfig() {
        JsonObject object = new JsonObject();
        object.addProperty("x", x);
        object.addProperty("y", y);
        object.addProperty("open", extended);
        return object;
    }

    @Override
    public void readConfig(JsonObject in) {
        x = in.get("x").getAsInt();
        y = in.get("y").getAsInt();
        extended = in.get("open").getAsBoolean();
    }
}
