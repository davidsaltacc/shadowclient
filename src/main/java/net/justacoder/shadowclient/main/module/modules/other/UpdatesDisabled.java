package net.justacoder.shadowclient.main.module.modules.other;

import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;


@SearchTags({"disable updates", "stop", "lagfix", "fix lag"})
public class UpdatesDisabled extends Module {
    public UpdatesDisabled() {
        super("updatesdisabled", "Disable Updates", "Disables the Hack Client update cycle completely.", ModuleCategory.OTHER);
    }
}
