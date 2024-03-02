package net.justacoder.shadowclient.main.module.modules.other;

import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.util.JavaUtils;

@OneClick
@SearchTags({"help", "documentation", "tutorial"})
public class AdvancedHelp extends Module {
    public AdvancedHelp() {
        super("advancedhelp", "Advanced Help", "Opens a more detailed Help inside your browser.", ModuleCategory.OTHER);
    }

    @Override
    public void onEnable() {
        JavaUtils.openBrowser("https://davidsaltacc.github.io/pages/minecraft/shadowclient");
        super.onEnable();
    }
}
