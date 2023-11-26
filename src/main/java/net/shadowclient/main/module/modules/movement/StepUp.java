package net.shadowclient.main.module.modules.movement;

import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.module.ModuleManager;
import net.shadowclient.main.setting.settings.NumberSetting;

@EventListener({PreTickEvent.class})
@SearchTags({"stepup", "step up", "step"})
public class StepUp extends Module {

    public final NumberSetting HEIGHT = new NumberSetting("Height", 1, 10, 1, 0);

    public StepUp() {
        super("stepup", "Stepup", "Walk onto blocks without having to jump.", ModuleCategory.MOVEMENT);

        addSetting(HEIGHT);
    }

    @Override
    public void onEvent(Event event) {

        mc.player.setBoundingBox(mc.player.getBoundingBox().offset(0, HEIGHT.floatValue(), 0));
        mc.player.setStepHeight(HEIGHT.floatValue());
        mc.player.setBoundingBox(mc.player.getBoundingBox().offset(0, -HEIGHT.floatValue(), 0));

    }

    @Override
    public void onEnable() {
        ModuleManager.SafeWalkModule.setDisabled(true, false); // incompatible
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.setStepHeight(0.6f);
        }
        super.onDisable();
    }
}
