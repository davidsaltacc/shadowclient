package net.shadowclient.main.module.modules.fun;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@SearchTags({"dinnerbone all", "dinnerbonifyall", "grumm all", "flip upside down"})
public class DinnerbonifyAll extends Module { // TODO test
    public DinnerbonifyAll() {
        super("dinnerbonifyall", "Dinnerbonify All", "Flips every living entity upside down.", ModuleCategory.FUN);
    }
}
