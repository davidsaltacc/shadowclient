package net.justacoder.shadowclient.main.module.modules.movement;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

@EventListener({ PreTickEvent.class })
public class StepDown extends Module {

    public StepDown() {
        super("stepdown", ModuleCategory.MOVEMENT, new String[]{ "stepdown", "step down", "fastfall", "fast fall", "gravity" });

        addSetting(MAX_DISTANCE);
    }

    private static final NumberSetting MAX_DISTANCE = new NumberSetting(new TranslatableString("setting.module.shadowclient.stepup.max_distance"), 1, 10, 1, 0);

    @Override
    public void onEvent(Event event) {

        if (mc.player.isOnGround() || mc.player.isSwimming() || mc.player.isGliding() || mc.player.noClip || mc.player.getAbilities().flying) {
            return;
        }

        if (mc.player.getVelocity().y > 0.1) {
            return;
        }

        BlockHitResult result0 = mc.world.raycast(new RaycastContext(mc.player.getPos(), mc.player.getPos().add(0, -MAX_DISTANCE.doubleValue(), 0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, mc.player));

        if (result0.getType() == HitResult.Type.MISS) {
            return;
        }

        float mxD = (float) Math.floor(mc.player.getPos().y - result0.getPos().y + 0.1);
        float mnD = mxD - 0.1f;

        BlockHitResult result = mc.world.raycast(new RaycastContext(mc.player.getPos().add(0, -mnD, 0), mc.player.getPos().add(0, -mxD, 0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, mc.player));

        if (result.getType() != HitResult.Type.MISS && !result.isInsideBlock() && mc.world.isSpaceEmpty(mc.player.getBoundingBox().offset(0., result.getPos().y - mc.player.getPos().y, 0.))) {
            Vec3d pos = result.getPos();
            ShadowClientMain.mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), true, mc.player.horizontalCollision));
            ShadowClientMain.mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), (mc.player.getY() + pos.y) / 2, mc.player.getZ(), true, mc.player.horizontalCollision));
            mc.player.setPosition(pos.x, pos.y, pos.z);
            ShadowClientMain.mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(pos.x, pos.y, pos.z, true, mc.player.horizontalCollision));
        }

    }

}
