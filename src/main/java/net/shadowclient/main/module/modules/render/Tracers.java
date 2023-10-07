package net.shadowclient.main.module.modules.render;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.Render3DEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.BooleanSetting;
import net.shadowclient.main.util.RenderUtils;

@SearchTags({"tracers", "lines", "entity tracers"})
public class Tracers extends Module {

    public final BooleanSetting DrawPlayerEntityTracers = new BooleanSetting("Players", true);
    public final BooleanSetting DrawHostileEntityTracers = new BooleanSetting("Hostiles", true);
    public final BooleanSetting DrawPassiveEntityTracers = new BooleanSetting("Passives", false);
    public final BooleanSetting DrawAmbientEntityTracers = new BooleanSetting("Ambients", false);
    public final BooleanSetting DrawOtherEntityTracers = new BooleanSetting("Others", true);

    public final float tracerAlpha = 0.6f;

    public final float maxRange = Float.MAX_VALUE;

    public Tracers() {
        super("tracers", "Tracers", ModuleCategory.RENDER);

        addSettings(DrawPlayerEntityTracers, DrawHostileEntityTracers, DrawPassiveEntityTracers, DrawAmbientEntityTracers, DrawOtherEntityTracers);
    }

    public float[] getColor(Entity entity) {
        float[] color;
        if (entity instanceof PlayerEntity) {
            color = new float[]{1f, 0f, 0f, DrawPlayerEntityTracers.booleanValue() ? tracerAlpha : 0f};
        } else if (entity instanceof Monster) {
            color = new float[]{1f, 0.5f, 0f, DrawHostileEntityTracers.booleanValue() ? tracerAlpha : 0f};
        } else if (entity instanceof PassiveEntity) {
            color = new float[]{0f, 1f, 0f, DrawPassiveEntityTracers.booleanValue() ? tracerAlpha : 0f};
        } else if (entity instanceof AmbientEntity) {
            color = new float[]{0f, 0f, 0.85f, DrawAmbientEntityTracers.booleanValue() ? tracerAlpha : 0f};
        } else {
            color = new float[]{0f, 1f, 1f, DrawOtherEntityTracers.booleanValue() ? tracerAlpha : 0f};
        }
        return color;
    }

    @Override
    public void OnEvent(Event event) {
        if (!(event instanceof Render3DEvent)) {
            return;
        }

        for (Entity entity : mc.world.getEntities()) {

            if (entity == mc.player) {
                continue;
            }
            if (mc.player.distanceTo(entity) > maxRange) {
                continue;
            }

            float[] col = getColor(entity);

            Vec3d vec = entity.getPos().subtract(RenderUtils.getInterpolationOffset(entity));
            Vec3d vec2 = new Vec3d(0, 0, 75).rotateX(-(float) Math.toRadians(mc.gameRenderer.getCamera().getPitch())).rotateY(-(float) Math.toRadians(mc.gameRenderer.getCamera().getYaw())).add(mc.cameraEntity.getEyePos());

            RenderUtils.drawLine(vec2.x, vec2.y, vec2.z, vec.x, vec.y, vec.z, col, 1);
            RenderUtils.drawLine(vec.x, vec.y, vec.z, vec.x, vec.y + entity.getHeight() * 0.9, vec.z, col, 1);

        }
    }

}
