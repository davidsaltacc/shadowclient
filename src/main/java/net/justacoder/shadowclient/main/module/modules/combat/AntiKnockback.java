package net.justacoder.shadowclient.main.module.modules.combat;

import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.KnockbackEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;

@SearchTags({"no knockback", "anti knockback"})
@EventListener({KnockbackEvent.class})
public class AntiKnockback extends Module {

    public final NumberSetting STRENGTH = new NumberSetting("Strength", 0.01f, 1f, 1f, 2);

    public AntiKnockback() {
        super("antiknockback", "No Knockback", "Don't take any knockback from attacks.", ModuleCategory.COMBAT);
        addSetting(STRENGTH);
    }

    @Override
    public void onEvent(Event event) {

        float multiplier = 1 - STRENGTH.floatValue();

        ((KnockbackEvent) event).x *= multiplier;
        ((KnockbackEvent) event).y *= multiplier;
        ((KnockbackEvent) event).z *= multiplier;
    }
}
