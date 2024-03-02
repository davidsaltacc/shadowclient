package net.justacoder.shadowclient.main.module.modules.movement;

import net.minecraft.util.math.Vec3d;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@EventListener({PreTickEvent.class})
@SearchTags({"spider", "wallclimb", "climb walls", "wall climb"})
public class Spider extends Module {

    public Spider() {
        super("spider", "Spider", "Get the ability to climb up walls.", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onEvent(Event event) {
        if (!mc.player.horizontalCollision) {
            return;
        }

        Vec3d velocity = mc.player.getVelocity();

        if (velocity.y >= 0.2) {
            return;
        }

        mc.player.setVelocity(velocity.x, 0.2, velocity.z);

    }
}
