package net.shadowclient.main.module.modules.combat;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.KnockbackEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.NumberSetting;

@SearchTags({"no knockback", "anti knockback"})
public class AntiKnockback extends Module {

    public final NumberSetting STRENGTH = new NumberSetting("Strength", 0.01f, 1f, 1f, 0.01f);

    public AntiKnockback() {
        super("antiknockback", "No Knockback", ModuleCategory.COMBAT);
        addSetting(STRENGTH);
    }

    @Override
    public void OnEvent(Event event) {
        if (!(event instanceof KnockbackEvent)) {
            return;
        }

        float multiplier = 1 - STRENGTH.floatValue();

        ((KnockbackEvent) event).x *= multiplier;
        ((KnockbackEvent) event).y *= multiplier;
        ((KnockbackEvent) event).z *= multiplier;
    }
}
