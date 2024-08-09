package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@SearchTags({"cameranoclip", "camera noclip", "camera clip", "camera see through"})
public class CameraNoclip extends Module {
    public CameraNoclip() {
        super("cameranoclip", ModuleCategory.RENDER);
    }
}
