package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.setting.settings.ColorSetting;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.setting.settings.PaddingSetting;
import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.justacoder.shadowclient.main.util.ColorUtils;
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

    public final BooleanSetting drawPlayerEntityTracers = new BooleanSetting(TranslatableString.of("setting.module.shadowclient.tracers.players"), true);
    public final BooleanSetting drawHostileEntityTracers = new BooleanSetting(TranslatableString.of("setting.module.shadowclient.tracers.hostiles"), false);
    public final BooleanSetting drawPassiveEntityTracers = new BooleanSetting(TranslatableString.of("setting.module.shadowclient.tracers.passives"), false);
    public final BooleanSetting drawAmbientEntityTracers = new BooleanSetting(TranslatableString.of("setting.module.shadowclient.tracers.ambients"), false);
    public final BooleanSetting drawOtherEntityTracers = new BooleanSetting(TranslatableString.of("setting.module.shadowclient.tracers.others"), true);

    public final ColorSetting playerTracerColor = new ColorSetting(TranslatableString.of("setting.module.shadowclient.tracers.players_color"), -65536);
    public final ColorSetting hostileTracerColor = new ColorSetting(TranslatableString.of("setting.module.shadowclient.tracers.hostiles_color"), -33024);
    public final ColorSetting passiveTracerColor = new ColorSetting(TranslatableString.of("setting.module.shadowclient.tracers.passives_color"), -16711936);
    public final ColorSetting ambientTracerColor = new ColorSetting(TranslatableString.of("setting.module.shadowclient.tracers.ambients_color"), -16776999);
    public final ColorSetting otherTracerColor = new ColorSetting(TranslatableString.of("setting.module.shadowclient.tracers.others_color"), -16711681);

    public final BooleanSetting drawNames = new BooleanSetting(TranslatableString.of("setting.module.shadowclient.tracers.show_names"), true);
    public final BooleanSetting drawDistance = new BooleanSetting(TranslatableString.of("setting.module.shadowclient.tracers.show_distance"), true);

    public final NumberSetting startDistance = new NumberSetting(TranslatableString.of("setting.module.shadowclient.tracers.text_distance"), 0.1, 20, 2, 1);

    public Tracers() {
        super("tracers", ModuleCategory.RENDER, new String[]{"tracers", "lines", "entity tracers", "esp"});

        addSettings(
                drawPlayerEntityTracers, drawHostileEntityTracers, drawPassiveEntityTracers, drawAmbientEntityTracers, drawOtherEntityTracers,
                new PaddingSetting(),
                playerTracerColor, hostileTracerColor, passiveTracerColor, ambientTracerColor, otherTracerColor,
                new PaddingSetting(),
                drawNames, drawDistance, startDistance
        );
    }

    public float[] getColor(Entity entity) {
        return switch (entity) {
            case PlayerEntity ignored -> ColorUtils.int2RGBAfloat(playerTracerColor.colorValue());
            case Monster ignored -> ColorUtils.int2RGBAfloat(hostileTracerColor.colorValue());
            case PassiveEntity ignored -> ColorUtils.int2RGBAfloat(passiveTracerColor.colorValue());
            case AmbientEntity ignored -> ColorUtils.int2RGBAfloat(ambientTracerColor.colorValue());
            case null, default -> ColorUtils.int2RGBAfloat(otherTracerColor.colorValue());
        };
    }

    @Override
    public void onEvent(Event event) {

        RenderEvent evt = (RenderEvent) event;

        Vec3d tracerStart = new Vec3d(0, 0, startDistance.floatValueEased()).rotateX(-(float) Math.toRadians(mc.gameRenderer.getCamera().getPitch())).rotateY(-(float) Math.toRadians(mc.gameRenderer.getCamera().getYaw())).add(mc.gameRenderer.getCamera().getPos());

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
