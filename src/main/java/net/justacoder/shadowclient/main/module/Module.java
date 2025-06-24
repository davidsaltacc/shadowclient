package net.justacoder.shadowclient.main.module;

import net.justacoder.shadowclient.main.annotations.NoChatMessages;
import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.justacoder.shadowclient.main.ui.settings.modules.SettingsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.ui.clickgui.ModuleButton;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {

    public final ModuleCategory category;
    public final String moduleId;
    public final String[] searchTags;
    public TranslatableString name;
    public TranslatableString description;

    public ModuleButton moduleButton = null;

    public KeyBinding keyBinding;

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

    public Module(String id, ModuleCategory category, String[] searchTags) {
        this.moduleId = id;
        this.category = category;
        this.searchTags = searchTags;
        this.name = TranslatableString.of("module.shadowclient." + this.moduleId);
        this.description = TranslatableString.of("module.description.shadowclient." + this.moduleId);
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

    public boolean onEnable() {
        if (showMessage && !this.getClass().isAnnotationPresent(OneClick.class) && !this.getClass().isAnnotationPresent(NoChatMessages.class)) {
            ShadowClientMain.moduleToggleChatMessage(name.getTranslation(), true);
        }
        updateSettingsScreenIfNecessary();
        return true;
    }

    public boolean onDisable() {
        if (showMessage && !this.getClass().isAnnotationPresent(OneClick.class) && !this.getClass().isAnnotationPresent(NoChatMessages.class)) {
            ShadowClientMain.moduleToggleChatMessage(name.getTranslation(), false);
        }
        updateSettingsScreenIfNecessary();
        return true;
    }

    private void updateSettingsScreenIfNecessary() {
        if (mc.currentScreen instanceof SettingsScreen screen && screen.enabledSetting != null && screen.enabledSetting.booleanValue() != enabled) {
            screen.enabledSetting.setBooleanValue(enabled, false);
        }
    }

    public void onEvent(Event event) {}

    public void postInit() {}

}
