package net.justacoder.shadowclient.main.module.modules.movement;

import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;

@EventListener({PreTickEvent.class})
public class AirJump extends Module {

    public AirJump() {
        super("airjump", ModuleCategory.MOVEMENT, new String[]{"airjump", "air jump", "jetpack"});

        addSetting(jetpackMode);
    }

    public static final BooleanSetting jetpackMode = new BooleanSetting("Jetpack Mode", false);

    @Override
    public void onEvent(Event event) {

        if (jetpackMode.booleanValue() ? mc.options.jumpKey.isPressed() : mc.options.jumpKey.wasPressed()) {
            mc.player.jump();
        }

    }

}
