package net.justacoder.shadowclient.main.module.modules.movement;

import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@SearchTags({"auto sneak", "autosneak", "auto crouch", "autocrouch"})
public class AutoSneak extends Module {

    public AutoSneak() {
        super("autosneak", ModuleCategory.MOVEMENT);
    }

}
