package net.justacoder.shadowclient.main.module.modules.other;

import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.module.ModuleManager;

@OneClick
public class Panic extends Module {
    public Panic() {
        super("panic", ModuleCategory.OTHER, new String[]{"panic", "disableall", "disable all", "stop all"});
    }

    @Override
    public void onEnable() {
        ModuleManager.getAllModules().forEach((name, module) -> {
            if (!name.equals("panic")) {
                module.setDisabled(true, false);
            }
        });
        super.onEnable();
    }
}
