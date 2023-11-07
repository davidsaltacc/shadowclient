package net.shadowclient.main.module.modules.render;

import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@ReceiveNoUpdates
@SearchTags({"nightvision", "night vision"})
public class NightVision extends Module { // TODO TEST
    public NightVision() {
        super("nightvision", "Night Vision", ModuleCategory.RENDER);
    }
}
