package net.justacoder.shadowclient.main.module.modules.movement;

import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.justacoder.shadowclient.main.util.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;

@EventListener({PreTickEvent.class})
public class Fly extends Module {
    private int toggle = 0;
    private double acceleration = 0.2;

    public final NumberSetting SPEED = new NumberSetting(new TranslatableString("setting.module.shadowclient.fly.speed"), 0.1f, 5f, 1f, 1, MathUtils.Easing.EASE_IN_QUADRATIC);

    public Fly() {
        super("fly", ModuleCategory.MOVEMENT, new String[]{"flyhack", "fly", "flying"});

        addSetting(SPEED);
    }

    @Override
    public void onEvent(Event event) {
        if (mc.player.isOnGround()) {
            acceleration = 0.2;
            return;
        }

        boolean jumpPressed = mc.options.jumpKey.isPressed();
        boolean forwardPressed = mc.options.jumpKey.isPressed();
        boolean leftPressed = mc.options.jumpKey.isPressed();
        boolean rightPressed = mc.options.jumpKey.isPressed();
        boolean backPressed = mc.options.jumpKey.isPressed();

        Entity entity = mc.player.hasVehicle() ? mc.player.getVehicle() : mc.player;

        Vec3d vel = entity.getVelocity();
        Vec3d newvel = new Vec3d(vel.x, -0.04, vel.z);

        if (jumpPressed) {
            if (forwardPressed) {
                newvel = mc.player.getRotationVector().multiply(acceleration);
            }
            if (leftPressed && !mc.player.hasVehicle()) {
                newvel = mc.player.getRotationVector().multiply(acceleration).rotateY(3.1415927f / 2);
                newvel = new Vec3d(newvel.x, 0, newvel.z);
            }
            if (rightPressed && !mc.player.hasVehicle()) {
                newvel = mc.player.getRotationVector().multiply(acceleration).rotateY(-3.1415927f / 2);
                newvel = new Vec3d(newvel.x, 0, newvel.z);
            }
            if (backPressed) {
                newvel = mc.player.getRotationVector().negate().multiply(acceleration);
            }

            newvel = new Vec3d(newvel.x, (toggle == 0 && newvel.y > 0.04) ? 0.04 : newvel.y, newvel.z);
            newvel = newvel.multiply(-1);
            entity.setVelocity(newvel);

            if (forwardPressed || leftPressed || rightPressed || backPressed) {
                if (acceleration < SPEED.floatValueEased()) {
                    acceleration += 0.1;
                }
            } else if (acceleration > 0.2) {
                acceleration -= 0.2;
            }

        }

        if (toggle == 0 || newvel.y <= -0.04) {
            toggle = 40;
        }
        toggle -= 1;

    }
}
