package net.shadowclient.main.module.modules.movement;

import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.NumberSetting;

@EventListener({PreTickEvent.class})
@SearchTags({"clicktp", "click teleport"})
public class ClickTP extends Module {

    public NumberSetting MAX_DISTANCE = new NumberSetting("Max Distance: ", 1, 100, 10, 2);

    public ClickTP() {
        super("clicktp", "ClickTP", "Teleports you to wherever you click (sprint key + use key).", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onEvent(Event event) {
        if (mc.player.isUsingItem()) {
            return;
        }
        if (mc.player.getActiveItem() != ItemStack.EMPTY) {
            return;
        }

        if (mc.options.sprintKey.isPressed() && mc.options.useKey.isPressed()) {
            BlockHitResult result = (BlockHitResult) mc.player.raycast(MAX_DISTANCE.floatValue(), 1f / 20f, false);

            BlockPos pos = result.getBlockPos();
            Direction dir = result.getSide();

            mc.player.setPosition(new Vec3d(pos.getX() + 0.5f + dir.getOffsetX(), pos.getY() + 1f, pos.getZ() + 0.5f + dir.getOffsetZ()));
        }
    }
}
