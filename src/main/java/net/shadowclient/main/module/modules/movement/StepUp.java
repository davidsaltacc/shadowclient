package net.shadowclient.main.module.modules.movement;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.NumberSetting;

@SearchTags({"stepup", "step up", "step"})
public class StepUp extends Module {

    public final NumberSetting HEIGHT = new NumberSetting("Height", 1, 10, 1, 1);

    public StepUp() {
        super("stepup", "Stepup", ModuleCategory.MOVEMENT);

        addSetting(HEIGHT);
    }

    @Override
    public void OnEvent(Event event) {
        if (!(event instanceof PreTickEvent)) {
            return;
        }

        mc.player.setBoundingBox(mc.player.getBoundingBox().offset(0, HEIGHT.floatValue(), 0));
        mc.player.setStepHeight(HEIGHT.floatValue());
        mc.player.setBoundingBox(mc.player.getBoundingBox().offset(0, -HEIGHT.floatValue(), 0));

    }

    @Override
    public void OnDisable() {
        mc.player.setStepHeight(0.6f);
    }
}
