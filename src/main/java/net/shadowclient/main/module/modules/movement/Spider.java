package net.shadowclient.main.module.modules.movement;

import net.minecraft.util.math.Vec3d;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@SearchTags({"spider", "wallclimb", "climb walls", "wall climb"})
public class Spider extends Module {

    public Spider() {
        super("spider", "Spider", ModuleCategory.MOVEMENT);
    }

    @Override
    public void OnEvent(Event event) {
        if (!(event instanceof PreTickEvent)) {
            return;
        }

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
