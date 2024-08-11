package net.justacoder.shadowclient.main.module;

import net.justacoder.shadowclient.main.annotations.NotKeybindable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.ui.clickgui.ModuleButton;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {

    public final ModuleCategory category;
    public final String moduleName;
    public String friendlyName;
    public String description;

    public ModuleButton moduleButton = null;

    public KeyBinding keyBinding;
    public String keyBindingName;

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

    public Module(String name, ModuleCategory category) {
        this.moduleName = name;
        this.category = category;
        this.friendlyName = "";
        this.description = "";
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
            SCMain.moduleToggleChatMessage(friendlyName, true);
        }
    }
    public void onDisable() {
        if (showMessage && !this.getClass().isAnnotationPresent(OneClick.class)) {
            SCMain.moduleToggleChatMessage(friendlyName, false);
        }
    }
    public void onEvent(Event event) {}

    public void postInit() {}

    public void reloadTranslations() {
        this.friendlyName = I18n.translate("module.shadowclient." + this.moduleName);
        this.description = I18n.translate("module.description.shadowclient." + this.moduleName);
        reloadKeybindTranslation();
    }

    public void reloadKeybindTranslation() {
        if (!this.getClass().isAnnotationPresent(NotKeybindable.class)) {
            this.keyBindingName = this.keyBinding.isUnbound() ? I18n.translate("name.shadowclient.none") : this.keyBinding.getBoundKeyLocalizedText().getString();
        }
    }

}
