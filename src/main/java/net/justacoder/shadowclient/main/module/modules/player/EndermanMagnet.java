package net.justacoder.shadowclient.main.module.modules.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;

@EventListener({PreTickEvent.class})
@SearchTags({"enderman look", "endermen magnet", "enderman", "look at enderman", "look", "magnet", "enderman magnet", "look at endermen"})
public class EndermanMagnet extends Module {

    public EndermanMagnet() {
        super("endermanlook", "Endermen Magnet", "Looks at all endermen visible.", ModuleCategory.OTHER);

    }

    @Override
    public void onEvent(Event event) {

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
