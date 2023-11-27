package net.shadowclient.main.module.modules.fun;

import net.shadowclient.main.annotations.OneClick;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.module.ModuleManager;

@OneClick
@SearchTags({"all hacks", "allhacks", "all modules", "allmodules"})
public class AllModules extends Module {
    public AllModules() {
        super("allmodules", "All Modules", "Enables all modules.", ModuleCategory.FUN);
    }

    @Override
    public void onEnable() {
        ModuleManager.getAllModules().forEach((n, m) -> {
            if (!m.getClass().isAnnotationPresent(OneClick.class)) {
                m.setEnabled();
            }
        });
        super.onEnable();
    }
}
