package net.shadowclient.main.module.modules.other;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@SearchTags({"enderman look", "endermen magnet", "enderman", "look at enderman", "look", "magnet", "enderman magnet", "look at endermen"})
public class EndermanLook extends Module {

    public EndermanLook() {
        super("endermanlook", "Endermen Magnet", ModuleCategory.OTHER);

    }

    @Override
    public void OnEvent(Event event) {
        if (!(event instanceof PreTickEvent)) {
            return;
        }

        for (Entity entity : mc.world.getEntities()) {
            if (!(entity instanceof EndermanEntity enderman)) {
                continue;
            }
            if (enderman.isAngry() || !enderman.isAlive() || !mc.player.canSee(enderman)) {
                continue;
            }
            double y = enderman.getEyeY();
            double diffX = entity.getX() - mc.player.getX();
            double diffY = y - (mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()));
            double diffZ = entity.getZ() - mc.player.getZ();
            double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(
                mc.player.getYaw() + MathHelper.wrapDegrees((float) Math.toDegrees(Math.atan2(enderman.getZ() - mc.player.getZ(), enderman.getX() - mc.player.getX())) - 90f - mc.player.getYaw()),
                mc.player.getPitch() + MathHelper.wrapDegrees((float) -Math.toDegrees(Math.atan2(diffY, diffXZ)) - mc.player.getPitch()),
                mc.player.isOnGround()
            ));
            break;
        }

    }

}
