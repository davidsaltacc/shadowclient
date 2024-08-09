package net.justacoder.shadowclient.main.module.modules.other;

import net.justacoder.shadowclient.main.annotations.DoNotSaveState;
import net.justacoder.shadowclient.main.annotations.NotKeybindable;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.module.ModuleManager;

@NotKeybindable
@DoNotSaveState
@SearchTags({"keybinds", "keybindings", "custom binds", "configure keys", "custom keys", "configure binds"})
public class ConfigureKeybindings extends Module {

    public ConfigureKeybindings() {
        super("configurekeybindings", ModuleCategory.OTHER);
    }

    @Override
    public void onEnable() {
        ModuleManager.startKeybindConfiguration();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        ModuleManager.endKeybindConfiguration();
        super.onDisable();
    }
}
