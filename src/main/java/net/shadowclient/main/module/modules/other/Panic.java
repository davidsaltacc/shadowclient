package net.shadowclient.main.module.modules.other;

import net.shadowclient.main.annotations.OneClick;
import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.module.ModuleManager;

@OneClick
@ReceiveNoUpdates
@SearchTags({"panic", "disableall", "disable all", "stop all"})
public class Panic extends Module {
    public Panic() {
        super("panic", "Panic", ModuleCategory.OTHER);
    }

    @Override
    public void OnEnable() {
        ModuleManager.getAllModules().forEach((name, module) -> {
            if (!name.equals("panic")) {
                module.setDisabled();
            }
        });
    }
}
