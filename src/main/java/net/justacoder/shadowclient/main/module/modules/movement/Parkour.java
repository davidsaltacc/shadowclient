package net.justacoder.shadowclient.main.module.modules.movement;

import net.justacoder.shadowclient.main.util.MathUtils;
import net.minecraft.util.math.Box;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.util.EntityUtils;

@EventListener({PreTickEvent.class})
public class Parkour extends Module {

    public final NumberSetting EDGE_DIST = new NumberSetting("Edge Distance", 0.001f, 0.25f, 0.001f, 3, MathUtils.Easing.EASE_IN_QUADRATIC);
    public final BooleanSetting LEGIT = new BooleanSetting("Legit", true);

    public Parkour() {
        super("parkour", ModuleCategory.MOVEMENT, new String[]{"parkour", "autojump", "auto jump", "auto parkour"});

        addSettings(EDGE_DIST, LEGIT);
    }

    @Override
    public void onEvent(Event event) {

        if (!mc.player.isOnGround() || mc.options.jumpKey.isPressed()) {
            return;
        }

        if (mc.player.isSneaking() || mc.options.sneakKey.isPressed()) {
            return;
        }

        Box box = mc.player.getBoundingBox();
        Box adjustedBox = box.stretch(0, -0.5, 0).expand(-EDGE_DIST.floatValueEased(), 0, -EDGE_DIST.floatValueEased());

        if (!mc.world.isSpaceEmpty(mc.player, adjustedBox)) {
            return;
        }

        mc.player.jump();
        if (LEGIT.booleanValue()) {
            EntityUtils.setOnGround(mc.player, false);
        }
    }
}
