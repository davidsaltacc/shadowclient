package net.justacoder.shadowclient.main.module.modules.other;

import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.util.JavaUtils;

@OneClick
public class AdvancedHelp extends Module {
    public AdvancedHelp() {
        super("advancedhelp", ModuleCategory.OTHER, new String[]{"help", "documentation", "tutorial"});
    }

    @Override
    public void onEnable() {
        JavaUtils.openBrowser("https://davidsaltacc.github.io/pages/minecraft/shadowclient");
        super.onEnable();
    }
}
