package net.justacoder.shadowclient.main.ui.clickgui;

import net.justacoder.shadowclient.main.annotations.NotKeybindable;
import net.justacoder.shadowclient.main.module.modules.menus.ConfigureKeybindings;
import net.justacoder.shadowclient.main.ui.clickgui.settings.modules.SettingsScreen;
import net.justacoder.shadowclient.main.ui.font.Font;
import net.minecraft.client.gui.DrawContext;
import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.annotations.Hidden;
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
import java.util.concurrent.atomic.AtomicInteger;

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

    private String getName() {
        if (ModuleManager.isConfiguringKeyBinds()) {
            if (module.getClass().isAnnotationPresent(NotKeybindable.class)) {
                return module.friendlyName;
            } else {
                return "[ " + module.keyBindingName + " ] " + module.friendlyName;
            }
        }
        return module.friendlyName;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        boolean hovered = isHovered(mouseX, mouseY);

        int color = Colors.MODULE_BUTTON_NORMAL.color;
        if (hovered) {
           color = Colors.MODULE_BUTTON_HOVERED.color;
        }
        context.fill(parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, color);
        int textOffset = (int) ((float) parent.height / 2 - Font.getHeight() / 2);

        Font.renderString(context, getName(), parent.x + textOffset, parent.y + offset + textOffset, getTextColor());

        if (extended) {
            for (SettingComponent component : components) {
                component.render(context, mouseX, mouseY, delta);
            }
        }
    }

    public void renderDescription(DrawContext context, int mouseX, int mouseY) {
        int color = Colors.MODULE_BUTTON_NORMAL.color;

        int width = Font.getWidth(module.description);
        int textOffset = (int) ((float) parent.height / 2 - Font.getHeight() / 2);

        context.fill(parent.x + parent.width, parent.y + offset, parent.x + parent.width + width + textOffset * 2, parent.y + offset + parent.height, color);

        Font.renderString(context, module.description, parent.x + parent.width + textOffset, parent.y + offset + textOffset, Colors.TEXT_NORMAL.color);
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
                if (ModuleManager.isConfiguringKeyBinds() && !module.getClass().isAnnotationPresent(NotKeybindable.class)) {
                    if (module instanceof ConfigureKeybindings) {
                        ShadowClientMain.toggleModuleEnabled(module.moduleName);
                        return;
                    }
                    ModuleManager.setConfiguringKeyBinding(module.keyBinding, module);
                    return;
                } else {
                    ShadowClientMain.toggleModuleEnabled(module.moduleName);
                }
            } else if (button == GLFW.GLFW_MOUSE_BUTTON_2) {
                ShadowClientMain.mc.setScreen(new SettingsScreen(module));
            }
        }

        for (SettingComponent component : components) {
            if (extended) {
                component.mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        for (SettingComponent component : components) {
            if (extended) {
                component.mouseReleased(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        for (SettingComponent component : components) {
            if (extended) {
                component.keyPressed(keyCode, scanCode, modifiers);
            }
        }
    }

    @Override
    public int getHeight() {
        AtomicInteger height = new AtomicInteger(parent.height);
        if (extended) {
            components.forEach(child -> height.set(height.get() + child.getHeight()));
        }
        return height.get();
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
        if (ModuleManager.isConfiguringKeyBinds()) {
            if ((module.keyBinding == ModuleManager.getConfiguringKeyBinding() && !module.getClass().isAnnotationPresent(NotKeybindable.class)) || module instanceof ConfigureKeybindings) {
                return Colors.TEXT_ENABLED.color;
            }
            return Colors.TEXT_NORMAL.color;
        }
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

}
