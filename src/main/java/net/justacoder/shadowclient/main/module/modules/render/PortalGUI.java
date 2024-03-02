package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@SearchTags({"portal ui", "portal gui", "portalgui", "nether portal inventory"})
public class PortalGUI extends Module {
    public PortalGUI() {
        super("portalgui", "Portal GUI", "Allows you to open your inventory while in portals.", ModuleCategory.RENDER);
    }
}
