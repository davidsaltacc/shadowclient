package net.shadowclient.main.module.modules.movement;

import net.minecraft.util.math.Box;
import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.BooleanSetting;
import net.shadowclient.main.setting.settings.NumberSetting;
import net.shadowclient.main.util.EntityUtils;

@EventListener({PreTickEvent.class})
@SearchTags({"parkour", "autojump", "auto jump", "auto parkour"})
public class Parkour extends Module {

    public final NumberSetting EDGE_DIST = new NumberSetting("Edge Distance", 0.001f, 0.25f, 0.001f, 0.001f);
    public final BooleanSetting LEGIT = new BooleanSetting("Legit", true);

    public Parkour() {
        super("parkour", "Parkour", "Automatically jump at the very edge of blocks. ", ModuleCategory.MOVEMENT);

        addSettings(EDGE_DIST, LEGIT);
    }

    @Override
    public void OnEvent(Event event) {

        if (!mc.player.isOnGround() || mc.options.jumpKey.isPressed()) {
            return;
        }

        if (mc.player.isSneaking() || mc.options.sneakKey.isPressed()) {
            return;
        }

        Box box = mc.player.getBoundingBox();
        Box adjustedBox = box.stretch(0, -0.5, 0).expand(-EDGE_DIST.floatValue(), 0, -EDGE_DIST.floatValue());

        if (!mc.world.isSpaceEmpty(mc.player, adjustedBox)) {
            return;
        }

        mc.player.jump();
        if (LEGIT.booleanValue()) {
            EntityUtils.setOnGround(mc.player, false);
        }
    }
}
