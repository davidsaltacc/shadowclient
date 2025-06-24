package net.justacoder.shadowclient.main.module.modules.fun;

import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.translations.TranslatableString;

@EventListener({PreTickEvent.class})
public class SneakSpam extends Module {

    public final NumberSetting SPEED = new NumberSetting(TranslatableString.of("setting.module.shadowclient.sneakspam.speed"), 1, 10, 5, 0);

    private int timer = 0;

    public SneakSpam() {
        super("sneakspam", ModuleCategory.FUN, new String[]{"sneak spam", "sneak", "twerk"});

        addSetting(SPEED);
    }

    @Override
    public void onEvent(Event event) {
        timer++;

        if (timer < 10 - SPEED.doubleValueEased()) {
            return;
        }

        mc.options.sneakKey.setPressed(!mc.options.sneakKey.isPressed());
        timer = -1;
    }
}
