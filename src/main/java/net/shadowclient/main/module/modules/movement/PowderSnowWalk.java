package net.shadowclient.main.module.modules.movement;

import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@ReceiveNoUpdates
@SearchTags({"snow walk", "powder snow walk", "leather boots walk", "anti snow sink", "no snow sinking", "snowwalk"})
public class PowderSnowWalk extends Module {
    public PowderSnowWalk() {
        super("powdersnowwalk", "Snowwalk", ModuleCategory.MOVEMENT);
    }
}
