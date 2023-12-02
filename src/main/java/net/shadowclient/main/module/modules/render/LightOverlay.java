package net.shadowclient.main.module.modules.render;

import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.LightType;
import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.Render3DEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.NumberSetting;
import net.shadowclient.main.util.RenderUtils;

@EventListener({Render3DEvent.class})
@SearchTags({"lightoverlay", "light overlay", "spawn indicator"})
public class LightOverlay extends Module {

    public NumberSetting RADIUS = new NumberSetting("Radius", 1, 25, 10, 0);

    public LightOverlay() {
        super("lightoverlay", "Light Overlay", "Renders an overlay on blocks indicating if mobs can/will spawn.", ModuleCategory.RENDER);

        addSetting(RADIUS);
    }

    @Override
    public void onEvent(Event event) {
        int plx = mc.player.getBlockX();
        int ply = mc.player.getBlockY();
        int plz = mc.player.getBlockZ();

        int radius = RADIUS.intValue();

        int minX = plx - radius;
        int maxX = plx + radius;
        int minZ = plz - radius;
        int maxZ = plz + radius;
        int minY = Math.max(mc.world.getBottomY(), ply - radius);
        int maxY = Math.min(ply + radius, mc.world.getTopY());

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = mc.world.getBlockState(pos);
                    int spawnPossible = spawnPossible(pos, state);
                    renderBlockOverlay(pos, spawnPossible);
                }
            }
        }
    }

    public void renderBlockOverlay(BlockPos pos, int level) { // todo maybe render text showing light level somehow?
        if (level == -1) {
            return;
        }
        float x = pos.getX();
        float y = pos.getY() + 0.008f;
        float z = pos.getZ();
        float[] color;
        if (level == 1) {
            color = new float[]{1f, 1f, 0f, 1f};
        } else if (level == 2) {
            color = new float[]{1f, 0f, 0f, 1f};
        } else {
            color = new float[]{0f, 1f, 0f, 1f};
        }
        RenderUtils.drawLine(x, y, z, x, y, z + 1, color, 1, true);
        RenderUtils.drawLine(x, y, z + 1, x + 1, y, z + 1, color, 1, true);
        RenderUtils.drawLine(x + 1, y, z + 1, x + 1, y, z, color, 1, true);
        RenderUtils.drawLine(x + 1, y, z, x, y, z, color, 1, true);
    }

    public int spawnPossible(BlockPos pos, BlockState state) { // -1 = do not render, 0 = never, 1 = possible, 2 = always
        if (!(state.getBlock() instanceof AirBlock)) {
            return -1;
        }
        BlockPos down = pos.down();
        BlockState dState = mc.world.getBlockState(down);
        if (dState.getBlock() instanceof AirBlock) {
            return -1;
        }
        if (dState.getBlock() == Blocks.BEDROCK) {
            return -1;
        }
        if (!top(dState)) {
            if (dState.getCollisionShape(mc.world, down) != VoxelShapes.fullCube()) {
                return -1;
            }
            if (dState.isTransparent(mc.world, down)) {
                return -1;
            }
        }
        if (mc.world.getLightLevel(pos, 0) <= 0) {
            return 2;
        } else if (mc.world.getLightLevel(LightType.BLOCK, pos) <= 0) {
            return 1;
        }
        return 0;
    }

    public boolean top(BlockState state) {
        if (state.getBlock() instanceof SlabBlock && state.get(SlabBlock.TYPE) == SlabType.TOP) {
            return true;
        } else {
            return state.getBlock() instanceof StairsBlock && state.get(StairsBlock.HALF) == BlockHalf.TOP;
        }
    }
}
