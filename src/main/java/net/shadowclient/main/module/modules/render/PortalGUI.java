package net.shadowclient.main.module.modules.render;

import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@ReceiveNoUpdates
@SearchTags({"portal ui", "portal gui", "portalgui", "nether portal inventory"})
public class PortalGUI extends Module {
    public PortalGUI() {
        super("portalgui", "Portal GUI", "Allows you to open your inventory while in portals.", ModuleCategory.RENDER);
    }
}
