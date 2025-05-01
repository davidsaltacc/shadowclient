package net.justacoder.shadowclient.main.module.modules.player;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

public class Reach extends Module {

    public Reach() {
        super("reach", ModuleCategory.PLAYER, new String[]{"reach", "reachhack", "reach hack"});
    }

    public float distance() {
        return 5f;
    }
}
