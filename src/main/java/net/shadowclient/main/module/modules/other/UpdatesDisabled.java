package net.shadowclient.main.module.modules.other;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;


@SearchTags({"disable updates", "stop", "lagfix", "fix lag"})
public class UpdatesDisabled extends Module {
    public UpdatesDisabled() {
        super("updatesdisabled", "Disable Updates", "Disables the Hack Client update cycle completely.", ModuleCategory.OTHER);
    }
}
