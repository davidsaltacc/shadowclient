package net.justacoder.shadowclient.main.module.modules.fun;

import net.justacoder.shadowclient.main.annotations.NotKeybindable;
import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.module.modules.render.Freecam;

@NotKeybindable
@OneClick
public class AllModules extends Module {
    public AllModules() {
        super("allmodules", ModuleCategory.FUN, new String[]{"all hacks", "allhacks", "all modules", "allmodules"});
    }

    @Override
    public boolean onEnable() {
        ModuleManager.getAllModules().forEach((n, m) -> {
            if (!m.getClass().isAnnotationPresent(OneClick.class) && !(m instanceof Freecam)) {
                m.setEnabled();
            }
        });
        return super.onEnable();
    }
}
