package net.shadowclient.main.module.modules.render;

import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@ReceiveNoUpdates
@SearchTags({"nightvision", "night vision", "caven vision"})
public class NightVision extends Module {
    public NightVision() {
        super("nightvision", "Night Vision", ModuleCategory.RENDER);
    }
}
