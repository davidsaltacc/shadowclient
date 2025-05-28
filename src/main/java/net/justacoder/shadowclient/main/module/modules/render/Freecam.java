package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.annotations.DoNotSaveState;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.*;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.util.ChatUtils;
import net.justacoder.shadowclient.main.util.MathUtils;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MarkerEntity;
import net.minecraft.util.math.Vec3d;

@DoNotSaveState
@EventListener({DamageEvent.class, PreTickEvent.class, MouseClickedEvent.class, MouseMoveEvent.class})
public class Freecam extends Module {

    public NumberSetting SPEED = new NumberSetting("Speed", 0.05f, 8, 1, 2, MathUtils.Easing.EASE_IN_QUADRATIC);

    public Freecam() {
        super("freecam", ModuleCategory.RENDER, new String[]{"freecam", "camera fly", "free cam"});
        addSetting(SPEED);
    }

    private Entity freecamEntity = null;

    @Override
    public void onEnable() {

        mc.options.setPerspective(Perspective.FIRST_PERSON);

        freecamEntity = new MarkerEntity(EntityType.MARKER, mc.world); // marker entity is the closest thing to an "empty" entity
        freecamEntity.setPosition(mc.player.getPos().add(new Vec3d(0, mc.player.getHeight() + 1f, 0)));
        freecamEntity.setYaw(mc.player.getYaw());
        freecamEntity.setYaw(mc.player.getPitch());
        freecamEntity.resetPosition();

        mc.setCameraEntity(freecamEntity);

        super.onEnable();
    }

    @Override
    public void onDisable() {

        if (freecamEntity == null) {
            return;
        }

        freecamEntity.remove(Entity.RemovalReason.DISCARDED);
        freecamEntity = null;

        mc.setCameraEntity(mc.player);

        super.onDisable();
    }

    @Override
    public void onEvent(Event event) {

        if (event instanceof DamageEvent) {
            setDisabled(true, false);
            ChatUtils.sendMessageClient("Toggled freecam because you took damage.");
        }

        if (event instanceof MouseClickedEvent && mc.currentScreen == null) {
            event.cancel();
        }

        if (event instanceof PreTickEvent) {

            float directionLR = 0f;
            float directionFB = 0f;
            float directionUD = 0f;

            if (mc.options.forwardKey.isPressed()) { directionFB += 1; }
            if (mc.options.backKey.isPressed()) { directionFB -= 1; }
            if (mc.options.leftKey.isPressed()) { directionLR += 1; }
            if (mc.options.rightKey.isPressed()) { directionLR -= 1; }
            if (mc.options.jumpKey.isPressed()) { directionUD += 1; }
            if (mc.options.sneakKey.isPressed()) { directionUD -= 1; }

            if (directionLR != 0f || directionFB != 0f || directionUD != 0f) {
                freecamEntity.setPosition(freecamEntity.getPos().add(new Vec3d(directionLR, directionUD, directionFB).rotateY((float) Math.toRadians(-freecamEntity.getYaw())).normalize().multiply(SPEED.floatValueEased() * (mc.options.sprintKey.isPressed() ? 4 : 1))));
                freecamEntity.resetPosition();
            }

        }

        if (event instanceof MouseMoveEvent evt) {

            freecamEntity.setYaw(freecamEntity.getYaw() + (float) evt.getDeltaX());
            freecamEntity.setPitch(freecamEntity.getPitch() + (float) evt.getDeltaY());
            freecamEntity.resetPosition();

            evt.cancel();

        }

    }

    public Entity getFreecamEntity() {
        return freecamEntity;
    }

}
