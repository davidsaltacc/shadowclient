package net.justacoder.shadowclient.main.module.modules.fun;

import net.justacoder.shadowclient.main.annotations.NotKeybindable;
import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.module.modules.other.ConfigureKeybindings;
import net.justacoder.shadowclient.main.module.modules.render.Freecam;

@NotKeybindable
@OneClick
@SearchTags({"all hacks", "allhacks", "all modules", "allmodules"})
public class AllModules extends Module {
    public AllModules() {
        super("allmodules", ModuleCategory.FUN);
    }

    @Override
    public void onEnable() {
        ModuleManager.getAllModules().forEach((n, m) -> {
            if (!m.getClass().isAnnotationPresent(OneClick.class) && !(m instanceof Freecam) && !(m instanceof ConfigureKeybindings)) {
                m.setEnabled();
            }
        });
        super.onEnable();
    }
}
