package net.justacoder.shadowclient.main.module.modules.player;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

public class NoLevitation extends Module {
    public NoLevitation() {
        super("nolevitation", ModuleCategory.PLAYER, new String[]{"no levitation", "anti levitation", "anti shulker"});
    }
}
