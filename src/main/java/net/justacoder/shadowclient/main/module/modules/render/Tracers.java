package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.ui.font.Font;
import net.justacoder.shadowclient.main.util.RotationUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.RenderEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;
import org.joml.Quaternionf;

@EventListener({RenderEvent.class})
public class Tracers extends Module {

    public final BooleanSetting drawPlayerEntityTracers = new BooleanSetting("Players", true);
    public final BooleanSetting drawHostileEntityTracers = new BooleanSetting("Hostiles", false);
    public final BooleanSetting drawPassiveEntityTracers = new BooleanSetting("Passives", false);
    public final BooleanSetting drawAmbientEntityTracers = new BooleanSetting("Ambients", false);
    public final BooleanSetting drawOtherEntityTracers = new BooleanSetting("Others", true);

    public final BooleanSetting drawNames = new BooleanSetting("Show Names", true);
    public final BooleanSetting drawDistance = new BooleanSetting("Show Distance", true);

    public final NumberSetting startDistance = new NumberSetting("Text Distance", 0.1, 20, 2, 1);

    public Tracers() {
        super("tracers", ModuleCategory.RENDER, new String[]{"tracers", "lines", "entity tracers", "esp"});

        addSettings(drawPlayerEntityTracers, drawHostileEntityTracers, drawPassiveEntityTracers, drawAmbientEntityTracers, drawOtherEntityTracers, drawNames, drawDistance, startDistance);
    }

    public float[] getColor(Entity entity) {
        return switch (entity) {
            case PlayerEntity ignored -> new float[]{1f, 0f, 0f, 1f};
            case Monster ignored -> new float[]{1f, 0.5f, 0f, 1f};
            case PassiveEntity ignored -> new float[]{0f, 1f, 0f, 1f};
            case AmbientEntity ignored -> new float[]{0f, 0f, 0.85f, 1f};
            case null, default -> new float[]{0f, 1f, 1f, 1f};
        };
    }

    @Override
    public void onEvent(Event event) {

        RenderEvent evt = (RenderEvent) event;

        Vec3d tracerStart = new Vec3d(0, 0, startDistance.floatValue()).rotateX(-(float) Math.toRadians(mc.gameRenderer.getCamera().getPitch())).rotateY(-(float) Math.toRadians(mc.gameRenderer.getCamera().getYaw())).add(mc.gameRenderer.getCamera().getPos());

        for (Entity entity : mc.world.getEntities()) {

            if (
                    (entity == mc.player && !ModuleManager.FreecamModule.enabled) ||
                    (ModuleManager.FreecamModule.enabled && entity == ModuleManager.FreecamModule.getFreecamEntity())
            ) {
                continue;
            }

            if (entity instanceof PlayerEntity && !drawPlayerEntityTracers.booleanValue()) { continue;
            } else if (entity instanceof Monster && !drawHostileEntityTracers.booleanValue()) { continue;
            } else if (entity instanceof PassiveEntity && !drawPassiveEntityTracers.booleanValue()) { continue;
            } else if (entity instanceof AmbientEntity && !drawAmbientEntityTracers.booleanValue()) { continue;
            } else if (
                    !(entity instanceof PlayerEntity) && !(entity instanceof Monster) && !(entity instanceof PassiveEntity) && !(entity instanceof AmbientEntity) &&
                    !drawOtherEntityTracers.booleanValue()) { continue;
            }

            float[] col = getColor(entity);

            Vec3d vec = entity.getPos().subtract(evt.renderer.getInterpolationOffset(entity));

            evt.renderer.drawLine(tracerStart.x, tracerStart.y, tracerStart.z, vec.x, vec.y, vec.z, col, false);
            evt.renderer.drawLine(vec.x, vec.y, vec.z, vec.x, vec.y + entity.getHeight(), vec.z, col, false);

            if (drawNames.booleanValue() || drawDistance.booleanValue()) {

                Vec3d diff = vec.subtract(tracerStart);
                Vec3d pointOnTracer = tracerStart.add(diff.normalize().multiply(Math.min(diff.length() / 20, 1)));
                Vec3d textPos = pointOnTracer.add(0, (pointOnTracer.y - tracerStart.y) < 0 ? 0.1 : -0.1, 0);

                evt.renderer.drawLine(pointOnTracer.x, pointOnTracer.y, pointOnTracer.z, textPos.x, textPos.y, textPos.z, col, false);

                float[] textRot = RotationUtils.rotationAwayF(textPos, mc.gameRenderer.getCamera().getPos());

                float offsetY = (pointOnTracer.y - tracerStart.y) < 0 ? (-evt.renderer.getTextHeight() * (drawNames.booleanValue() && drawDistance.booleanValue() ? 2 : 1) - 1f) : 1f;

                if (drawNames.booleanValue()) {
                    String name = entity.getName().getString();
                    evt.renderer.drawText(name, textPos.x, textPos.y, textPos.z, new Quaternionf().rotationYXZ(-(float) Math.toRadians(textRot[0]), (float) Math.toRadians(textRot[1]), 0f), -1, -evt.renderer.getTextWidth(name) / 2f, offsetY);
                    offsetY += evt.renderer.getTextHeight();
                }

                if (drawDistance.booleanValue()) {
                    String distance = (int) Math.floor(mc.player.getPos().distanceTo(entity.getPos())) + "m";
                    evt.renderer.drawText(distance, textPos.x, textPos.y, textPos.z, new Quaternionf().rotationYXZ(-(float) Math.toRadians(textRot[0]), (float) Math.toRadians(textRot[1]), 0f), -1, -evt.renderer.getTextWidth(distance) / 2f, offsetY);
                    offsetY += evt.renderer.getTextHeight();
                }

            }

        }

        // TODO make something like a facePlayer thing (much easier in the future)

    }

}
