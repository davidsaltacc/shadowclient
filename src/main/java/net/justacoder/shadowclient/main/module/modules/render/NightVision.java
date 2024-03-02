package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@SearchTags({"nightvision", "night vision", "caven vision"})
public class NightVision extends Module {
    public NightVision() {
        super("nightvision", "Night Vision", "Allows you to see in darkness.", ModuleCategory.RENDER);
    }
}
