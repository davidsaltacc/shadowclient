package net.shadowclient.main.module.modules.render;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.*;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.mixininterface.ISimpleOption;
import java.util.Collections;
import java.util.List;

@SearchTags({"xray", "x ray", "ore render"})
@EventListener({PacketRecievedEvent.class, SetOpaqueCubeEvent.class, GetAmbientOcclusionLightLevelEvent.class, ShouldDrawSideEvent.class, RenderBlockEntityEvent.class})
public class XRay extends Module {
    public XRay() {
        super("xray", "XRay", "Only render ores.", ModuleCategory.RENDER);
    }

    public List<String> oresToRender = List.of(
        "minecraft:ancient_debris", "minecraft:chest",
        "minecraft:coal_block", "minecraft:coal_ore",
        "minecraft:copper_ore", "minecraft:deepslate_coal_ore",
        "minecraft:deepslate_copper_ore", "minecraft:deepslate_diamond_ore",
        "minecraft:deepslate_emerald_ore", "minecraft:deepslate_gold_ore",
        "minecraft:deepslate_iron_ore", "minecraft:deepslate_lapis_ore",
        "minecraft:deepslate_redstone_ore", "minecraft:diamond_block",
        "minecraft:diamond_ore", "minecraft:emerald_block", "minecraft:emerald_ore",
        "minecraft:glowstone", "minecraft:gold_block", "minecraft:gold_ore",
        "minecraft:iron_block", "minecraft:iron_ore", "minecraft:lapis_block",
        "minecraft:lapis_ore", "minecraft:nether_gold_ore", "minecraft:nether_portal",
        "minecraft:nether_quartz_ore", "minecraft:raw_copper_block",
        "minecraft:raw_gold_block", "minecraft:raw_iron_block", "minecraft:redstone_block",
        "minecraft:redstone_ore", "minecraft:suspicious_gravel",
        "minecraft:suspicious_sand", "minecraft:tnt", "minecraft:water", "minecraft:lava"
    );

    @Override
    public void onEvent(Event event) {
        if (event instanceof PreTickEvent) {
            ISimpleOption.getFromOption(mc.options.getGamma()).forceSet(16.);
            return;
        }
        if (event instanceof SetOpaqueCubeEvent) {
            event.cancel();
            return;
        }
        if (event instanceof GetAmbientOcclusionLightLevelEvent evt) {
            evt.setLightLevel(1);
            return;
        }
        if (event instanceof ShouldDrawSideEvent evt) {
            evt.setRendered(visible(evt.state.getBlock(), evt.pos));
            return;
        }
        if (event instanceof RenderBlockEntityEvent evt) {
            BlockPos pos = evt.blockEntity.getPos();
            if (!visible(mc.world.getBlockState(pos).getBlock(), pos)) {
                event.cancel();
            }
        }
    }

    public boolean visible(Block block, BlockPos pos) {
        String n = Registries.BLOCK.getId(block).toString();
        int i = Collections.binarySearch(oresToRender, n);
        return i >= 0;
    }
}
