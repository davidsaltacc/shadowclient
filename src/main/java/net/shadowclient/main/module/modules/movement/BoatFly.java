package net.shadowclient.main.module.modules.movement;

import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@ReceiveNoUpdates
@SearchTags({"boat fly", "boatfly", "fly hack", "flyhack"})
public class BoatFly extends Module { // TODO TEST
    public BoatFly() {
        super("boatfly", "Boat Fly", ModuleCategory.MOVEMENT);
    }

    // TODO code lol
}
