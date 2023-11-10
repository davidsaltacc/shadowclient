package net.shadowclient.main.module.modules.movement;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@SearchTags({"no slowdown", "no block slowdown", "noslowdown"})
public class NoSlowdown extends Module {
    public NoSlowdown() {
        super("noslowdown", "No Slowdown", "Stops items and blocks from slowing you. ", ModuleCategory.MOVEMENT);
    }
}
