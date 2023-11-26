package net.shadowclient.main.module.modules.combat;

import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.KnockbackEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.NumberSetting;

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
