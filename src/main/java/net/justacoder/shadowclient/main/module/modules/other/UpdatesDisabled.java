package net.justacoder.shadowclient.main.module.modules.other;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

public class UpdatesDisabled extends Module {
    public UpdatesDisabled() {
        super("updatesdisabled", ModuleCategory.OTHER, new String[]{"disable updates", "stop", "lagfix", "fix lag"});
    }
}
