package net.justacoder.shadowclient.main.module.modules.render;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.RenderEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.util.PlayerUtils;
import net.justacoder.shadowclient.main.render.Renderer;
import net.justacoder.shadowclient.main.util.WorldUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@EventListener({RenderEvent.class})
@SearchTags({"trajectories", "bow aim laser", "aim assist"})
public class Trajectories extends Module {
    public Trajectories() {
        super("trajectories", ModuleCategory.RENDER);
    }

    @Override
    public void onEvent(Event event) {

        RenderEvent evt = (RenderEvent) event;

        Trajectory traj = getTrajectory(evt.renderer);

        float[] color = switch (traj.hitType) {
            case HitResult.Type.ENTITY -> new float[]{1f, 0.1f, 0.1f, 0.8f};
            case HitResult.Type.BLOCK -> new float[]{0.1f, 0.3f, 1f, 0.8f};
            default -> new float[]{1f, 1f, 1f, 0.8f};
        };

        evt.renderer.drawLineList(traj.path, color, false);

    }

    private record Trajectory(HitResult.Type hitType, List<Vec3d> path) {}

    public Trajectory getTrajectory(Renderer renderer) {

        ArrayList<Vec3d> trajPath = new ArrayList<>();
        HitResult.Type trajHit = HitResult.Type.MISS;

        Item item = mc.player.getMainHandStack().getItem(); // todo offhand too

        if (!(item instanceof RangedWeaponItem || item instanceof SnowballItem || item instanceof EggItem || item instanceof EnderPearlItem || item instanceof ThrowablePotionItem || item instanceof FishingRodItem || item instanceof TridentItem)) {
            return new Trajectory(trajHit, trajPath);
        }

        double power;

        if (!(item instanceof RangedWeaponItem)) {
            power = 1.5;
        } else {
            power = (72000 - mc.player.getItemUseTimeLeft()) / 20F;
            power = power * power + power * 2F;

            if (power > 3 || power <= 0.3F) {
                power = 3;
            }
        }

        double gravity = switch (item) {
            case RangedWeaponItem ignored -> 0.05;
            case ThrowablePotionItem ignored -> 0.4;
            case FishingRodItem ignored -> 0.15;
            case TridentItem ignored -> 0.015;
            default -> 0.03;
        };

        double yaw = Math.toRadians(mc.player.getYaw());
        double pitch = Math.toRadians(mc.player.getPitch());

        Vec3d arrowPos = mc.player.getPos().subtract(renderer.getInterpolationOffset(mc.player)).add(PlayerUtils.getHandOffset(Hand.MAIN_HAND, yaw));

        double cospitch = Math.cos(pitch);
        Vec3d arrowMotion = new Vec3d(-Math.sin(yaw) * cospitch, -Math.sin(pitch), Math.cos(yaw) * cospitch).normalize().multiply(power);

        for (int i = 0; i < 1000; i++) {
            trajPath.add(arrowPos);

            arrowPos = arrowPos.add(arrowMotion.multiply(0.1));

            arrowMotion = arrowMotion.multiply(0.999);

            arrowMotion = arrowMotion.add(0, -gravity * 0.1, 0);

            Vec3d lastPos = trajPath.size() > 1 ? trajPath.get(trajPath.size() - 2) : mc.player.getEyePos();

            HitResult result = item instanceof FishingRodItem ? WorldUtils.raycastFluidsSolid(lastPos, arrowPos) : WorldUtils.raycast(lastPos, arrowPos);

            if (result.getType() != HitResult.Type.MISS) {
                trajHit = HitResult.Type.BLOCK;
                trajPath.set(trajPath.size() - 1, result.getPos());
                break;
            }

            Box box = new Box(lastPos, arrowPos);
            Predicate<Entity> predicate = e -> !e.isSpectator() && e.canHit();
            double maxD = 4096;
            EntityHitResult result1 = ProjectileUtil.raycast(mc.player, lastPos, arrowPos, box, predicate, maxD);

            if (result1 != null && result1.getType() != HitResult.Type.MISS) {
                trajHit = HitResult.Type.ENTITY;
                trajPath.set(trajPath.size() - 1, result1.getPos());
                break;
            }
        }

        return new Trajectory(trajHit, trajPath);
        
    }

}
