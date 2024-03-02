package net.justacoder.shadowclient.main.module.modules.other;

import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;

@SearchTags({"unfocusedfps", "lag reducer", "dynamic fps"})
public class UnfocusedFPS extends Module {

    public NumberSetting TARGET_FPS = new NumberSetting("Target FPS", 1, 30, 1, 0);

    public UnfocusedFPS() {
        super("unfocusedfps", "UnfocusedFPS", "Decreases the FPS when the game is unfocused.", ModuleCategory.OTHER);
    }

    public int getFps() {
        return TARGET_FPS.intValue();
    }
}
