package net.shadowclient.main.module;

import net.minecraft.client.MinecraftClient;
import net.shadowclient.main.annotations.OneClick;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.setting.Setting;
import java.util.ArrayList;
import java.util.List;

public abstract class Module {

    public final ModuleCategory Category;
    public final String ModuleName;
    public final String FriendlyName;

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

    public Module(String name, String friendlyName, ModuleCategory category) {
        ModuleName = name;
        Category = category;
        FriendlyName =  friendlyName;
    }

    public void setEnabled() {
        this.enabled = true;
        this.OnEnable();
        if (this.getClass().isAnnotationPresent(OneClick.class)) {
            setDisabled();
        }
    }
    public void setDisabled() {
        this.enabled = false;
        this.OnDisable();
    }

    public void setEnabled(boolean event) {
        if (event) {
            this.OnEnable();
        }
        this.enabled = true;
    }
    public void setDisabled(boolean event) {
        if (event) {
            this.OnDisable();
        }
        this.enabled = false;
    }

    public void OnEnable() {
    }
    public void OnDisable() {
    }
    public void OnEvent(Event event) {
    }

}
