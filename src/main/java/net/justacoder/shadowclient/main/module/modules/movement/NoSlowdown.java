package net.justacoder.shadowclient.main.module.modules.movement;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

public class NoSlowdown extends Module {
    public NoSlowdown() {
        super("noslowdown", ModuleCategory.MOVEMENT, new String[]{"no slowdown", "no block slowdown", "noslowdown"});
    }
}
