package net.shadowclient.main.module.modules.other;

import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@ReceiveNoUpdates
@SearchTags({"no welcome message", "no message", "disable welcome"})
public class DisableWelcomeMessage extends Module {

    public DisableWelcomeMessage() {
        super("disablewelcomemessage", "Disable Welcome", ModuleCategory.OTHER);
    }

}
