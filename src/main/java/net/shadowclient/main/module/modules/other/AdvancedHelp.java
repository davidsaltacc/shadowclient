package net.shadowclient.main.module.modules.other;

import net.shadowclient.main.annotations.OneClick;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.util.JavaUtils;

@OneClick
@SearchTags({"help", "documentation", "tutorial"})
public class AdvancedHelp extends Module {
    public AdvancedHelp() {
        super("advancedhelp", "Advanced Help", "Opens a more detailed Help inside your browser.", ModuleCategory.OTHER);
    }

    @Override
    public void onEnable() {
        JavaUtils.openBrowser("https://davidsaltacc.github.io/pages/minecraft/shadowclient");
    }
}
