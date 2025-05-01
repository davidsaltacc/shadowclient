package net.justacoder.shadowclient.main.module.modules.movement;

import net.justacoder.shadowclient.main.util.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;

@EventListener({PreTickEvent.class})
public class BoatFly extends Module {

    NumberSetting SPEED = new NumberSetting("Speed", 0.02f, 10, 0.4,  2, MathUtils.Easing.EASE_IN_QUADRATIC);

    public BoatFly() {
        super("boatfly", ModuleCategory.MOVEMENT, new String[]{"boat fly", "boatfly", "fly hack", "flyhack"});

        addSetting(SPEED);
    }

    @Override
    public void onEvent(Event event) {
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
