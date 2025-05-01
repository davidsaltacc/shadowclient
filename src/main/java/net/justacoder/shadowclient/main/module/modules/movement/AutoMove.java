package net.justacoder.shadowclient.main.module.modules.movement;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

public class AutoMove extends Module {

    public AutoMove() {
        super("automove", ModuleCategory.MOVEMENT, new String[]{"automove", "movement", "auto", "walk", "run", "strafe", "auto walk", "auto move", "auto run"});
    }

}
