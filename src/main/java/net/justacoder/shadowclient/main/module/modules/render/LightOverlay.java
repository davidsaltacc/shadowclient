package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.LightType;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.RenderEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;

@EventListener({RenderEvent.class})
public class LightOverlay extends Module {

    public NumberSetting RADIUS = new NumberSetting(new TranslatableString("setting.module.shadowclient.lightoverlay.hradius"), 1, 25, 10, 0);
    public NumberSetting VRADIUS = new NumberSetting(new TranslatableString("setting.module.shadowclient.lightoverlay.vradius"), 1, 25, 1, 0);

    public LightOverlay() {
        super("lightoverlay", ModuleCategory.RENDER, new String[]{"lightoverlay", "light overlay", "spawn indicator"});

        addSettings(RADIUS, VRADIUS);
    }

    @Override
    public void onEvent(Event event) {

        int plx = mc.player.getBlockX();
        int ply = mc.player.getBlockY();
        int plz = mc.player.getBlockZ();

        int radius = RADIUS.intValueEased();
        int vradius = VRADIUS.intValueEased();

        int minX = plx - radius;
        int maxX = plx + radius;
        int minZ = plz - radius;
        int maxZ = plz + radius;
        int minY = Math.max(mc.world.getBottomY(), ply - vradius);
        int maxY = Math.min(ply + vradius, mc.world.getTopYInclusive());

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = mc.world.getBlockState(pos);
                    int spawnPossible = spawnPossible(pos, state);
                    renderBlockOverlay((RenderEvent) event, pos, spawnPossible);
                }
            }
        }
    }

    public void renderBlockOverlay(RenderEvent event, BlockPos pos, int level) { // todo maybe render text showing light level somehow?
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
        event.renderer.drawLine(x, y, z, x, y, z + 1, color, true);
        event.renderer.drawLine(x, y, z + 1, x + 1, y, z + 1, color, true);
        event.renderer.drawLine(x + 1, y, z + 1, x + 1, y, z, color, true);
        event.renderer.drawLine(x + 1, y, z, x, y, z, color, true);
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
            if (dState.isTransparent()) {
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
