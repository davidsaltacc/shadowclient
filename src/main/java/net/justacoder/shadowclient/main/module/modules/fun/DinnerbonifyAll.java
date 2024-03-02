package net.justacoder.shadowclient.main.module.modules.fun;

import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@SearchTags({"dinnerbone all", "dinnerbonifyall", "grumm all", "flip upside down"})
public class DinnerbonifyAll extends Module {
    public DinnerbonifyAll() {
        super("dinnerbonifyall", "Dinnerbonify All", "Flips every living entity upside down.", ModuleCategory.FUN);
    }
}
