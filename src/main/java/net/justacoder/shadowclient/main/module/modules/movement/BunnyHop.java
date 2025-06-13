package net.justacoder.shadowclient.main.module.modules.movement;

import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.EnumSetting;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.translations.TranslatableString;

@EventListener({PreTickEvent.class})
public class BunnyHop extends Module {
    public final EnumSetting<JumpWhen> MODE = new EnumSetting<>(new TranslatableString("setting.module.shadowclient.bunnyhop.mode"), JumpWhen.ALWAYS);
    public final NumberSetting MINVEL = new NumberSetting(new TranslatableString("setting.module.shadowclient.bunnyhop.minimum_velocity"), 0f, 0.3f, 0.075f, 3);

    public boolean pressed = false;

    public BunnyHop() {
        super("bunnyhop", ModuleCategory.MOVEMENT, new String[]{"bunny hop", "bunnyhop", "auto jump", "sprint jump"});

        addSettings(MODE, MINVEL);
    }

    @Override
    public void onEvent(Event event) {

        if (!mc.player.isOnGround() || mc.player.isSneaking()) {
            if (pressed) {
                mc.options.jumpKey.setPressed(false);
                pressed = false;
            }
            return;
        }
        if ((Math.abs(mc.player.getVelocity().x) + Math.abs(mc.player.getVelocity().z)) / 2f <= MINVEL.floatValueEased()) {
            return;
        }

        JumpWhen mode = MODE.getEnumValue();
        boolean sprinting = mc.player.isSprinting();

        boolean press = (mode == JumpWhen.SPRINTING && sprinting) || (mode == JumpWhen.WALKING && !sprinting) || (mode == JumpWhen.ALWAYS);
        mc.options.jumpKey.setPressed(press);
        pressed = press;
    }

    @Override
    public boolean onDisable() {
        if (pressed) {
            mc.options.jumpKey.setPressed(false);
            pressed = false;
        }
        return super.onDisable();
    }

    public enum JumpWhen {
        ALWAYS("Always"), SPRINTING("Sprinting"), WALKING("Walking");

        public final String name;
        JumpWhen(String name) {
            this.name = name;
        }
        @Override
        public String toString() {
            return name;
        }
    }
}
