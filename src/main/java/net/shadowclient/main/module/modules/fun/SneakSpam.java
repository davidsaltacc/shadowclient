package net.shadowclient.main.module.modules.fun;

import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.NumberSetting;

@EventListener({PreTickEvent.class})
@SearchTags({"sneak spam", "sneak", "twerk"})
public class SneakSpam extends Module {

    public final NumberSetting SPEED = new NumberSetting("Speed", 1, 10, 5, 0);

    private int timer = 0;

    public SneakSpam() {
        super("sneakspam", "Sneak Spam", "Silly. Spams the sneak key.", ModuleCategory.FUN);

        addSetting(SPEED);
    }

    @Override
    public void onEvent(Event event) {
        timer++;

        if (timer < 10 - SPEED.doubleValue()) {
            return;
        }

        mc.options.sneakKey.setPressed(!mc.options.sneakKey.isPressed());
        timer = -1;
    }
}
