package net.shadowclient.main.module.modules.movement;

import net.minecraft.util.math.Vec3d;
import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@EventListener({PreTickEvent.class})
@SearchTags({"fastclimb", "fast climbing", "climb fast", "speed climb"})
public class FastClimb extends Module {
    public FastClimb() {
        super("fastclimb", "Fast Climbing", "Allows you to climb ladders & etc. faster.", ModuleCategory.MOVEMENT);
    }

    @Override
    public void OnEvent(Event event) {

        if (!mc.player.isClimbing() || !mc.player.horizontalCollision) {
            return;
        }

        if (mc.player.input.movementForward == 0 && mc.player.input.movementSideways == 0 && mc.player.getVelocity().y <= 0) {
            return;
        }

        Vec3d velocity = mc.player.getVelocity();
        mc.player.setVelocity(velocity.x, 0.2872, velocity.z);

    }
}
