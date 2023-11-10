package net.shadowclient.main.module.modules.render;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@SearchTags({"no pumpkin", "anti pumpkin", "no pumkin"})
public class NoPumkin extends Module {
    public NoPumkin() {
        super("nopumpkin", "No Pumpkin", "No pumpkin overlay when wearing one.", ModuleCategory.RENDER);
    }

}
