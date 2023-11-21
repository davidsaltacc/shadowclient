package net.shadowclient.main.module.modules.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.Render3DEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.util.PlayerUtils;
import net.shadowclient.main.util.WorldUtils;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import java.util.ArrayList;
import java.util.function.Predicate;

@EventListener({Render3DEvent.class})
@SearchTags({"trajectories", "bow aim laser", "aim assist"})
public class Trajectories extends Module {
    public Trajectories() {
        super("trajectories", "Trajectories", "Draws a line where projectiles will go.", ModuleCategory.RENDER);
    }

    public ArrayList<Vec3d> trajPath;
    public HitResult.Type trajHit;

    @Override
    public void onEvent(Event event) {
        Render3DEvent evt = (Render3DEvent) event;

        evt.matrices.push();

        getTrajectory(evt.tickDelta);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        drawLine(evt.matrices, trajPath);
        
        if (!trajPath.isEmpty()) {
            drawEnd(evt.matrices, trajPath.get(trajPath.size() - 1));
        }

        RenderSystem.setShaderColor(1, 1, 1, 1);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        evt.matrices.pop();

    }

    public void getTrajectory(float delta) {

        trajHit = HitResult.Type.MISS;
        trajPath = new ArrayList<>();

        Item item = mc.player.getMainHandStack().getItem(); // todo offhand too

        if (!(item instanceof RangedWeaponItem || item instanceof SnowballItem || item instanceof EggItem || item instanceof EnderPearlItem || item instanceof ThrowablePotionItem || item instanceof FishingRodItem || item instanceof TridentItem)) {
            return;
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

        double gravity;

        if (item instanceof RangedWeaponItem) {
            gravity = 0.05;
        } else if (item instanceof ThrowablePotionItem) {
            gravity = 0.4;
        } else if (item instanceof FishingRodItem) {
            gravity = 0.15;
        } else if (item instanceof TridentItem) {
            gravity = 0.015;
        } else {
            gravity = 0.03;
        }

        double yaw = Math.toRadians(mc.player.getYaw());
        double pitch = Math.toRadians(mc.player.getPitch());

        Vec3d arrawPos = new Vec3d(MathHelper.lerp(delta, mc.player.lastRenderX, mc.player.getX()),
            MathHelper.lerp(delta, mc.player.lastRenderY, mc.player.getY()),
            MathHelper.lerp(delta, mc.player.lastRenderZ, mc.player.getZ())).add(PlayerUtils.getHandOffset(Hand.MAIN_HAND, yaw));

        double cospitch = Math.cos(pitch);
        Vec3d arrowMotion = new Vec3d(-Math.sin(yaw) * cospitch, -Math.sin(pitch), Math.cos(yaw) * cospitch).normalize().multiply(power);

        for (int i = 0; i < 1000; i++) {
            trajPath.add(arrawPos);

            arrawPos = arrawPos.add(arrowMotion.multiply(0.1));

            arrowMotion = arrowMotion.multiply(0.999);

            arrowMotion = arrowMotion.add(0, -gravity * 0.1, 0);

            Vec3d lastPos = trajPath.size() > 1 ? trajPath.get(trajPath.size() - 2) : mc.player.getEyePos();

            BlockHitResult result = WorldUtils.raycast(lastPos, arrawPos);

            if (result.getType() != HitResult.Type.MISS) {
                trajHit = HitResult.Type.BLOCK;
                trajPath.set(trajPath.size() - 1, result.getPos());
                break;
            }

            Box box = new Box(lastPos, arrawPos);
            Predicate<Entity> predicate = e -> !e.isSpectator() && e.canHit();
            double maxD = 4096;
            EntityHitResult result1 = ProjectileUtil.raycast(mc.player, lastPos, arrawPos, box, predicate, maxD);

            if (result1 != null && result1.getType() != HitResult.Type.MISS) {
                trajHit = HitResult.Type.ENTITY;
                trajPath.set(trajPath.size() - 1, result1.getPos());
                break;
            }
        }
        
    }

    public void drawLine(MatrixStack matrices, ArrayList<Vec3d> path) {
        Vec3d camPos = mc.getBlockEntityRenderDispatcher().camera.getPos();
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION);

        RenderSystem.setShaderColor(1f, 0.1f,  0.1f, 0.75F);

        path.forEach(point -> bufferBuilder.vertex(matrix, (float) (point.x - camPos.x), (float) (point.y - camPos.y), (float) (point.z - camPos.z)).next());

        tessellator.draw();

    }

    public void drawEnd(MatrixStack matrices, Vec3d pos) {
        // todo
    }

}
