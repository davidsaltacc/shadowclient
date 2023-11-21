package net.shadowclient.main.module.modules.movement;

import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.BooleanSetting;
import net.shadowclient.main.setting.settings.EnumSetting;
import net.shadowclient.main.setting.settings.NumberSetting;
import net.shadowclient.main.util.EntityUtils;

@EventListener({PreTickEvent.class})
@SearchTags({"bunny hop", "bunnyhop", "auto jump", "sprint jump"})
public class BunnyHop extends Module {
    public final EnumSetting<JumpWhen> MODE = new EnumSetting<>("When", JumpWhen.ALWAYS);
    public final BooleanSetting LEGIT = new BooleanSetting("Legit", true);
    public final NumberSetting MINVEL = new NumberSetting("Min. Velocity", 0f, 0.3f, 0.08f, 0.01f);

    public BunnyHop() {
        super("bunnyhop", "Bunny Hopping", "Spam the jump key, basically.", ModuleCategory.MOVEMENT);

        addSettings(MODE, LEGIT, MINVEL);
    }

    @Override
    public void onEvent(Event event) {

        if (!mc.player.isOnGround() || mc.player.isSneaking() || (Math.abs(mc.player.getVelocity().x) + Math.abs(mc.player.getVelocity().z)) / 2f <= MINVEL.floatValue()) {
            return;
        }

        JumpWhen mode = MODE.getEnumValue();
        boolean sprinting = mc.player.isSprinting();
        boolean legit = LEGIT.booleanValue();

        if (mode == JumpWhen.SPRINTING && sprinting) {
            mc.player.jump();
        }
        if (mode == JumpWhen.WALKING && !sprinting) {
            mc.player.jump();
        }
        if (mode == JumpWhen.ALWAYS) {
            mc.player.jump();
        }
        if (legit) {
            EntityUtils.setOnGround(mc.player, false);
        }
    }

    private enum JumpWhen {
        ALWAYS, SPRINTING, WALKING
    }
}
