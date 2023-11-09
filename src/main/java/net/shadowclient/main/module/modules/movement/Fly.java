package net.shadowclient.main.module.modules.movement;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.NumberSetting;

@SearchTags({"flyhack", "fly", "flying"})
public class Fly extends Module {
    private int toggle = 0;
    private double acceleration = 0.2;

    public final NumberSetting SPEED = new NumberSetting("Speed", 0.1f, 5f, 1f, 0.1f);

    public Fly() {
        super("fly", "Fly", "Fly. Thats it. Its in the name.", ModuleCategory.MOVEMENT);

        addSetting(SPEED);
    }

    @Override
    public void OnEvent(Event event) {
        if (!(event instanceof PreTickEvent)) {
            return;
        }
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
                if (acceleration < SPEED.floatValue()) {
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
