package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.annotations.DontSaveState;
import net.justacoder.shadowclient.main.util.EntityCullingFix;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.*;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.EnumSetting;
import java.util.Collections;
import java.util.List;

@DontSaveState
@SearchTags({"xray", "x ray", "ore render", "mine help", "finder"})
@EventListener({SetOpaqueCubeEvent.class, GetAmbientOcclusionLightLevelEvent.class, ShouldDrawSideEvent.class, RenderBlockEntityEvent.class})
public class XRay extends Module { // todo maybe add option to render blocks translucently or something

    public EnumSetting<Mode> MODE = new EnumSetting<>("Mode", Mode.All);

    public XRay() {
        super("xray", "XRay", "Only render ores.", ModuleCategory.RENDER);

        addSetting(MODE);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof SetOpaqueCubeEvent) {
            event.cancel();
            return;
        }
        if (event instanceof GetAmbientOcclusionLightLevelEvent evt) {
            evt.setLightLevel(1);
            return;
        }
        if (event instanceof ShouldDrawSideEvent evt) {
            evt.setRendered(visible(evt.state.getBlock()));
            return;
        }
        if (event instanceof RenderBlockEntityEvent evt) {
            BlockPos pos = evt.blockEntity.getPos();
            if (!visible(mc.world.getBlockState(pos).getBlock())) {
                event.cancel();
            }
        }
    }

    @Override
    public void onEnable() {
        EntityCullingFix.disableCull();
        if (mc.worldRenderer != null) {
            mc.worldRenderer.reload();
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        EntityCullingFix.enableCull();
        if (mc.worldRenderer != null) {
            mc.worldRenderer.reload();
        }
        super.onDisable();
    }


    public boolean visible(Block block) {
        String n = Registries.BLOCK.getId(block).toString();
        int i = Collections.binarySearch(MODE.getEnumValue().blocks, n);
        return i >= 0;
    }

    public enum Mode {
        All(
            List.of(
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
                "minecraft:suspicious_sand", "minecraft:tnt", "minecraft:water", "minecraft:lava",
                "minecraft:brewing_stand", "minecraft:crafting_table", "minecraft:furnace",
                "minecraft:blast_furnace", "minecraft:smoker", "minecraft:anvil",
                "minecraft:chipped_anvil", "minecraft:damaged_anvil",
                "minecraft:sculk", "minecraft:sculk_sensor", "minecraft:sculk_shrieker",
                "minecraft:bell", "minecraft:beacon", "minecraft:end_portal_frame",
                "minecraft:enchanting_table", "minecraft:command_block",
                "minecraft:chain_command_block", "minecraft:repeating_command_block",
                "minecraft:item_frame", "minecraft:glow_item_frame",
                "minecraft:small_amethyst_bud", "minecraft:medium_amethyst_bud",
                "minecraft:large_amethyst_bud", "minecraft:amethyst_cluster",
                "minecraft:budding_amethyst", "minecraft:respawn_anchor",
                "minecraft:mossy_stone_bricks", "minecraft:spawner",
                "minecraft:mob_spawner", "minecraft:nether_bricks",
                "minecraft:obsidian", "minecraft:stone_bricks",
                "minecraft:cracked_stone_bricks", "minecraft:mossy_stone_bricks",
                "minecraft:chiseled_stone_bricks", "minecraft:infested_stone",
                "minecraft:infested_cobblestone", "minecraft:infested_stone_bricks",
                "minecraft:infested_cracked_stone_bricks", "minecraft:infested_mossy_stone_bricks",
                "minecraft:infested_chiseled_stone_bricks", "minecraft:infested_deepslate",
                "minecraft:oak_log", "minecraft:spruce_log", "minecraft:birch_log",
                "minecraft:jungle_log", "minecraft:acacia_log", "minecraft:dark_oak_log",
                "minecraft:mangrove_log", "minecraft:cherry_log", "minecraft:crimson_stem",
                "minecraft:warped_stem", "minecraft:oak_planks", "minecraft:spruce_planks",
                "minecraft:birch_planks", "minecraft:jungle_planks", "minecraft:acacia_planks",
                "minecraft:dark_oak_planks", "minecraft:mangrove_planks", "minecraft:cherry_planks",
                "minecraft:bamboo_planks", "minecraft:crimson_planks", "minecraft:warped_planks",
                "minecraft:bamboo"
            )
        ), Ores(
            List.of(
                "minecraft:ancient_debris", "minecraft:coal_block", "minecraft:coal_ore",
                "minecraft:copper_ore", "minecraft:deepslate_coal_ore",
                "minecraft:deepslate_copper_ore", "minecraft:deepslate_diamond_ore",
                "minecraft:deepslate_emerald_ore", "minecraft:deepslate_gold_ore",
                "minecraft:deepslate_iron_ore", "minecraft:deepslate_lapis_ore",
                "minecraft:deepslate_redstone_ore", "minecraft:diamond_block",
                "minecraft:diamond_ore", "minecraft:emerald_block", "minecraft:emerald_ore",
                "minecraft:gold_block", "minecraft:gold_ore",
                "minecraft:iron_block", "minecraft:iron_ore", "minecraft:lapis_block",
                "minecraft:lapis_ore", "minecraft:nether_gold_ore", "minecraft:nether_quartz_ore",
                "minecraft:raw_copper_block", "minecraft:raw_gold_block", "minecraft:raw_iron_block",
                "minecraft:redstone_block", "minecraft:redstone_ore", "minecraft:suspicious_gravel",
                "minecraft:suspicious_sand"
            )
        ), Functional(
            List.of(
                "minecraft:chest", "minecraft:glowstone",  "minecraft:nether_portal",
                "minecraft:tnt", "minecraft:water", "minecraft:lava",
                "minecraft:brewing_stand", "minecraft:crafting_table", "minecraft:furnace",
                "minecraft:blast_furnace", "minecraft:smoker", "minecraft:anvil",
                "minecraft:chipped_anvil", "minecraft:damaged_anvil",
                "minecraft:bell", "minecraft:beacon", "minecraft:end_portal_frame",
                "minecraft:enchanting_table", "minecraft:command_block",
                "minecraft:chain_command_block", "minecraft:repeating_command_block",
                "minecraft:item_frame", "minecraft:glow_item_frame", "minecraft:respawn_anchor",
                "minecraft:spawner", "minecraft:mob_spawner"
            )
        ), NaturallySpawning(
            List.of(
                "minecraft:chest", "minecraft:glowstone", "minecraft:end_portal_frame",
                "minecraft:suspicious_gravel", "minecraft:suspicious_sand",
                "minecraft:sculk", "minecraft:sculk_sensor", "minecraft:sculk_shrieker",
                "minecraft:small_amethyst_bud", "minecraft:medium_amethyst_bud",
                "minecraft:large_amethyst_bud", "minecraft:amethyst_cluster",
                "minecraft:budding_amethyst", "minecraft:mossy_stone_bricks", "minecraft:spawner",
                "minecraft:mob_spawner", "minecraft:nether_bricks", "minecraft:obsidian", "minecraft:stone_bricks",
                "minecraft:cracked_stone_bricks", "minecraft:mossy_stone_bricks",
                "minecraft:chiseled_stone_bricks", "minecraft:infested_stone",
                "minecraft:infested_cobblestone", "minecraft:infested_stone_bricks",
                "minecraft:infested_cracked_stone_bricks", "minecraft:infested_mossy_stone_bricks",
                "minecraft:infested_chiseled_stone_bricks", "minecraft:infested_deepslate",
                "minecraft:oak_log", "minecraft:spruce_log", "minecraft:birch_log",
                "minecraft:jungle_log", "minecraft:acacia_log", "minecraft:dark_oak_log",
                "minecraft:mangrove_log", "minecraft:cherry_log", "minecraft:crimson_stem",
                "minecraft:warped_stem", "minecraft:oak_planks", "minecraft:spruce_planks",
                "minecraft:birch_planks", "minecraft:jungle_planks", "minecraft:acacia_planks",
                "minecraft:dark_oak_planks"
            )
        );

        public final List<String> blocks;

        Mode(List<String> blocks) {
            this.blocks = blocks;
        }
    }
}
