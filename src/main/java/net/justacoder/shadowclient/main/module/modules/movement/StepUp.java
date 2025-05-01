package net.justacoder.shadowclient.main.module.modules.movement;

import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.minecraft.entity.attribute.EntityAttributes;

@EventListener({PreTickEvent.class})
public class StepUp extends Module {

    public final NumberSetting HEIGHT = new NumberSetting("Height", 1, 10, 1, 0);

    public StepUp() {
        super("stepup", ModuleCategory.MOVEMENT, new String[]{"stepup", "step up", "step"});

        addSetting(HEIGHT);
    }

    public void setStepHeight(float height) {
        mc.player.getAttributes().getCustomInstance(EntityAttributes.STEP_HEIGHT).setBaseValue(height);
    }

    @Override
    public void onEvent(Event event) {

        mc.player.setBoundingBox(mc.player.getBoundingBox().offset(0, HEIGHT.floatValue(), 0));
        setStepHeight(HEIGHT.floatValue());
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
            setStepHeight(0.6f);
        }
        super.onDisable();
    }
}
