package net.justacoder.shadowclient.main.module.modules.player;

import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@SearchTags({"reach", "reachhack", "reach hack"})
public class Reach extends Module {

    public Reach() {
        super("reach", "Reach", "Extended reach.", ModuleCategory.PLAYER);
    }

    public float distance() {
        return 5f;
    }
}
