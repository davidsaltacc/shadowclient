package net.justacoder.shadowclient.main.ui.clickgui;

import net.minecraft.client.gui.DrawContext;
import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.annotations.Hidden;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;
import net.justacoder.shadowclient.main.setting.settings.EnumSetting;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.setting.settings.StringSetting;
import net.justacoder.shadowclient.main.ui.clickgui.settings.clickgui.SettingComponent;
import net.justacoder.shadowclient.main.ui.clickgui.settings.clickgui.components.BoolSetting;
import net.justacoder.shadowclient.main.ui.clickgui.settings.clickgui.components.ModeSetting;
import net.justacoder.shadowclient.main.ui.clickgui.settings.clickgui.components.SliderSetting;
import net.justacoder.shadowclient.main.ui.clickgui.settings.clickgui.components.TextSetting;
import org.lwjgl.glfw.GLFW;
import java.util.ArrayList;
import java.util.List;

public class ModuleButton extends FrameChild {

    public final Module module;
    public final Frame parent;
    public int offset;
    public boolean extended;

    public final List<SettingComponent> components;

    public ModuleButton(String modulename, Frame parent, int offset) {
        this.module = ModuleManager.getModule(modulename);
        this.parent = parent;
        this.offset = offset;
        this.components = new ArrayList<>();
        this.extended = false;

        int settingOffset = parent.height;
        for (Setting setting : module.getSettings()) {
            if (setting.getClass().isAnnotationPresent(Hidden.class)) {
                continue;
            }
            if (setting instanceof BooleanSetting) {
                components.add(new BoolSetting(setting, this, settingOffset));
            } else if (setting instanceof EnumSetting<?>) {
                components.add(new ModeSetting(setting, this, settingOffset));
            } else if (setting instanceof NumberSetting) {
                components.add(new SliderSetting(setting, this, settingOffset));
            } else if (setting instanceof StringSetting) {
                components.add(new TextSetting(setting, this, settingOffset));
            }
            settingOffset += parent.height;
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        boolean hovered = isHovered(mouseX, mouseY);

        int color = Colors.MODULE_BUTTON_NORMAL.color;
        if (hovered) {
           color = Colors.MODULE_BUTTON_HOVERED.color;
        }
        context.fill(parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, color);
        int textOffset = (parent.height / 2 - parent.mc.textRenderer.fontHeight / 2);

        context.drawTextWithShadow(parent.mc.textRenderer, module.friendlyName, parent.x + textOffset, parent.y + offset + textOffset, getTextColor());

        if (extended) {
            for (SettingComponent component : components) {
                component.render(context, mouseX, mouseY, delta);
            }
        }
    }

    public void renderDescription(DrawContext context, int mouseX, int mouseY) {
        int color = Colors.MODULE_BUTTON_NORMAL.color;

        int width = parent.mc.textRenderer.getWidth(module.description);
        int textOffset = (parent.height / 2 - parent.mc.textRenderer.fontHeight / 2);

        context.fill(parent.x + parent.width, parent.y + offset, parent.x + parent.width + width + textOffset * 2, parent.y + offset + parent.height, color);

        context.drawTextWithShadow(parent.mc.textRenderer, module.description, parent.x + parent.width + textOffset, parent.y + offset + textOffset, Colors.TEXT_NORMAL.color);
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
                SCMain.toggleModuleEnabled(module.moduleName);
            } else if (button == GLFW.GLFW_MOUSE_BUTTON_2) {
                extended = !extended;
                parent.updateButtons();
            }
        }

        for (SettingComponent component : components) {
            if (extended) {
                component.mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        for (SettingComponent component : components) {
            if (extended) {
                component.mouseReleased(mouseX, mouseY, button);
            }
        }
    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        for (SettingComponent component : components) {
            if (extended) {
                component.keyPressed(keyCode, scanCode, modifiers);
            }
        }
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.x && mouseX < parent.x + parent.width && mouseY > parent.y + offset && mouseY < parent.y + offset + parent.height;
    }

    public boolean isGettingSearchedFor() {
        String[] moduleSearchTags = module.getClass().getAnnotation(SearchTags.class).value();
        for (String searchtag : moduleSearchTags) {
            if (searchtag.toLowerCase().contains(SCMain.clickGui.searchingFor.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public int getTextColor() { // messy code I know but it works
        if (SCMain.clickGui.searching) {
            if (isGettingSearchedFor()) {
                if (module.enabled) {
                    return Colors.TEXT_ENABLED.color;
                } else {
                    return Colors.TEXT_NORMAL.color;
                }
            } else {
                return Colors.TEXT_DISABLED.color;
            }
        } else {
            if (module.enabled) {
                return Colors.TEXT_ENABLED.color;
            } else {
                return Colors.TEXT_NORMAL.color;
            }
        }
    }

}
