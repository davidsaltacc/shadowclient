package net.justacoder.shadowclient.main.module.modules.movement;

import net.justacoder.shadowclient.main.event.events.MouseClickedEvent;
import net.justacoder.shadowclient.main.util.BypassUtils;
import net.justacoder.shadowclient.main.util.MathUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

@EventListener({MouseClickedEvent.class})
@SearchTags({"clicktp", "click teleport"})
public class ClickTP extends Module {

    public NumberSetting MAX_DISTANCE = new NumberSetting("Max Distance: ", 1, 100, 10, 2, MathUtils.Easing.EASE_IN_QUADRATIC);

    public ClickTP() {
        super("clicktp", ModuleCategory.MOVEMENT);

        addSetting(MAX_DISTANCE);
    }

    @Override
    public void onEvent(Event event) {

        if (mc.player.isUsingItem()) {
            return;
        }
        if (mc.player.getActiveItem() != ItemStack.EMPTY) {
            return;
        }

        HitResult hitResult = mc.player.raycast(MAX_DISTANCE.doubleValue(), 1 / 20f, false);
        BlockPos pos = ((BlockHitResult) hitResult).getBlockPos();

        if (mc.options.sprintKey.isPressed() && mc.options.useKey.matchesMouse(((MouseClickedEvent) event).button) && hitResult.getType() == HitResult.Type.BLOCK) {
            BypassUtils.sendFiveMovementPackets();
            mc.player.setPosition(pos.toCenterPos());
        }

    }
}
