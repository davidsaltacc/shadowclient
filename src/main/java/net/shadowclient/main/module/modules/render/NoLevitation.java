package net.shadowclient.main.module.modules.render;

import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@ReceiveNoUpdates
@SearchTags({"no levitation", "anti levitation", "anti shulker"})
public class NoLevitation extends Module {
    public NoLevitation() {
        super("nolevitation", "No Levitation", ModuleCategory.RENDER);
    }
}
