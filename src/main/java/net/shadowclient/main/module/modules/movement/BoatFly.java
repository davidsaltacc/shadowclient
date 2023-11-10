package net.shadowclient.main.module.modules.movement;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.NumberSetting;

@EventListener({PreTickEvent.class})
@SearchTags({"boat fly", "boatfly", "fly hack", "flyhack"})
public class BoatFly extends Module {

    NumberSetting SPEED = new NumberSetting("Speed", 0.1f, 10, 1,  0.f);

    public BoatFly() {
        super("boatfly", "Boat Fly", "Allows you to fly in boats.", ModuleCategory.MOVEMENT);

        addSetting(SPEED);
    }

    @Override
    public void OnEvent(Event event) {
        if (!mc.player.hasVehicle()) {
            return;
        }

        float speed = SPEED.floatValue();

        Entity entity = mc.player.getVehicle();
        Vec3d vel = entity.getVelocity();
        double x = vel.x;
        double y = 0;
        double z = vel.z;
        if (mc.options.jumpKey.isPressed()) {
            y = speed;
        } else if (mc.options.sprintKey.isPressed()) {
            y = vel.y;
        }

        if (mc.options.forwardKey.isPressed()) {
            float yr = entity.getYaw() * MathHelper.RADIANS_PER_DEGREE;

            x = MathHelper.sin(-yr) * speed;
            z = MathHelper.cos(yr) * speed;
        }

        entity.setVelocity(x, y, z);
    }
}
