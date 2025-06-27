package net.justacoder.shadowclient.main.ui.clickgui;

import net.justacoder.shadowclient.main.annotations.NoSettingsScreen;
import net.justacoder.shadowclient.main.ui.Colors;
import net.justacoder.shadowclient.main.ui.animation.Animatable;
import net.justacoder.shadowclient.main.ui.settings.modules.SettingsScreen;
import net.justacoder.shadowclient.main.ui.font.Font;
import net.justacoder.shadowclient.main.util.MathUtils;
import net.minecraft.client.gui.DrawContext;
import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.module.Module;
import org.lwjgl.glfw.GLFW;

public class ModuleButton extends FrameChild implements Animatable {

    public final Module module;
    public final Frame parent;
    public int offset;

    public double animProgress = 0;
    public double animDuration = 0.5;
    public boolean descOpens = true;

    public ModuleButton(Module module, Frame parent, int offset) {
        this.module = module;
        this.parent = parent;
        this.offset = offset;
    }

    private String getName() {
        return module.name.getTranslation();
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        boolean hovered = isHovered(mouseX, mouseY);

        int color = Colors.MODULE_BUTTON_NORMAL.color;
        if (hovered) {
           color = Colors.MODULE_BUTTON_HOVERED.color;
        }
        if (descOpens != hovered) {
            descOpens = hovered;
        }

        context.fill(parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, color);
        int textOffset = (int) ((float) parent.height / 2 - Font.getHeight() / 2);

        Font.renderString(context, getName(), parent.x + textOffset, parent.y + offset + textOffset, getTextColor());

        progressAnimation();

    }

    public void renderDescription(DrawContext context, int mouseX, int mouseY) {
        int color = Colors.MODULE_BUTTON_NORMAL.color;

        int width = Font.getWidth(module.description);
        int textOffset = (int) ((float) parent.height / 2 - Font.getHeight() / 2);

        int w = (int) Math.floor((width + textOffset * 2) * (descOpens ? MathUtils.Easing.EASE_OUT_CUBIC : MathUtils.Easing.EASE_IN_CUBIC).eased(animProgress));

        context.enableScissor(parent.x + parent.width, parent.y + offset, parent.x + parent.width + w, parent.y + offset + parent.height);

        context.fill(parent.x + parent.width, parent.y + offset, parent.x + parent.width + width + textOffset * 2, parent.y + offset + parent.height, color);
        Font.renderString(context, module.description, parent.x + parent.width + textOffset, parent.y + offset + textOffset, Colors.TEXT_NORMAL.color);

        context.disableScissor();
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
                ShadowClientMain.toggleModuleEnabled(module.moduleId);
            } else if (button == GLFW.GLFW_MOUSE_BUTTON_2 && !module.getClass().isAnnotationPresent(NoSettingsScreen.class)) {
                ShadowClientMain.mc.setScreen(new SettingsScreen(module));
            }
        }

    }

    @Override
    public int getHeight() {
        return parent.height;
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.x && mouseX < parent.x + parent.width && mouseY > parent.y + offset && mouseY < parent.y + offset + parent.height;
    }

    public boolean isGettingSearchedFor() {
        for (String searchtag : module.searchTags) {
            if (searchtag.toLowerCase().contains(ShadowClientMain.clickGui.searchingFor.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public int getTextColor() {
        if (ShadowClientMain.clickGui.searching) {
            if (isGettingSearchedFor()) {
                if (module.enabled) {
                    return Colors.TEXT_ENABLED.color;
                }
                return Colors.TEXT_NORMAL.color;
            }
            return Colors.TEXT_DISABLED.color;
        } else {
            if (module.enabled) {
                return Colors.TEXT_ENABLED.color;
            }
            return Colors.TEXT_NORMAL.color;
        }
    }

    @Override
    public void setAnimProgress(double progress) {
        animProgress = progress;
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
        return descOpens;
    }

    @Override
    public void setAnimProgressesUp(boolean up) {}
}
