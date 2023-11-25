package net.shadowclient.main.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.shadowclient.main.SCMain;
import net.shadowclient.main.annotations.OneClick;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.setting.Setting;
import net.shadowclient.main.ui.clickgui.ModuleButton;
import java.util.ArrayList;
import java.util.List;

public abstract class Module {

    public final ModuleCategory category;
    public final String moduleName;
    public final String friendlyName;
    public final String description;

    public KeyBinding keybinding;

    public ModuleButton moduleButton = null;

    public boolean enabled;

    public List<Setting> getSettings() {
        return settings;
    }

    public void addSetting(Setting setting) {
        settings.add(setting);
    }

    public void addSettings(Setting...settings) {
        for (Setting setting : settings) {
            addSetting(setting);
        }
    }

    public final List<Setting> settings = new ArrayList<>();

    public final MinecraftClient mc = MinecraftClient.getInstance();

    public Module(String name, String friendlyName, String description, ModuleCategory category) {
        moduleName = name;
        this.category = category;
        this.friendlyName =  friendlyName;
        this.description = description;
    }

    public void setEnabled() {
        this.enabled = true;
        this.onEnable();
        if (this.getClass().isAnnotationPresent(OneClick.class)) {
            setDisabled();
        }
    }
    public void setDisabled() {
        this.enabled = false;
        this.onDisable();
    }

    public void setEnabled(boolean event) {
        if (event) {
            this.onEnable();
        }
        this.enabled = true;
    }
    public void setDisabled(boolean event) {
        if (event) {
            this.onDisable();
        }
        this.enabled = false;
    }

    public boolean showMessage = true;

    public void setEnabled(boolean event, boolean message) {
        if (event) {
            boolean msgOld = showMessage;
            showMessage = message;
            this.onEnable();
            showMessage = msgOld;
        }
        this.enabled = true;
    }
    public void setDisabled(boolean event, boolean message) {
        if (event) {
            boolean msgOld = showMessage;
            showMessage = message;
            this.onDisable();
            showMessage = msgOld;
        }
        this.enabled = false;
    }

    public void toggle() {
        if (enabled) {
            setDisabled();
            return;
        }
        setEnabled();
    }

    public void onEnable() {
        if (showMessage && !this.getClass().isAnnotationPresent(OneClick.class)) {
            SCMain.moduleToggleChatMessage(friendlyName);
        }
    }
    public void onDisable() {
        if (showMessage && !this.getClass().isAnnotationPresent(OneClick.class)) {
            SCMain.moduleToggleChatMessage(friendlyName);
        }
    }
    public void onEvent(Event event) {}

    public void postInit() {}

}
