package com.corruptionmod;

import com.corruptionmod.block.CleansingAltarBlock;
import com.corruptionmod.block.CorruptionAltarBlock;
import com.corruptionmod.block.CorruptionBlock;
import com.corruptionmod.block.CorruptedGrassBlock;
import com.corruptionmod.block.CorruptedSandBlock;
import com.corruptionmod.block.CorruptedStoneBlock;
import com.corruptionmod.block.RottedWoodBlock;
import com.corruptionmod.block.SacredBarrierBlock;
import com.corruptionmod.block.TaintedWaterBlock;
import com.corruptionmod.block.VoidPortalBlock;
import com.corruptionmod.block.VoidStoneBlock;
import com.corruptionmod.block.CorruptionFleshBlock;
import com.corruptionmod.block.ObsidianCrustBlock;
import com.corruptionmod.block.TwistedLogBlock;
import com.corruptionmod.block.BlightedGrassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

/**
 * Enregistre les blocs spéciaux du mod.
 */
public class ModBlocks {
    public static Block CORRUPTION_BLOCK;
    public static Block CORRUPTION_ALTAR;
    public static Block VOID_PORTAL_FRAME;

    // Converted variants
    public static Block CORRUPTED_GRASS;
    public static Block CORRUPTED_DIRT;
    public static Block CORRUPTED_STONE;
    public static Block ROTTED_WOOD;
    public static Block CORRUPTED_SAND;
    public static Block TAINTED_WATER;
    public static Block WITHERED_LEAVES;
    public static Block PURIFICATION_CRYSTAL;
    
    // Purification system blocks
    public static Block CLEANSING_ALTAR;
    public static Block WARDING_TORCH;
    public static Block WARDING_WALL_TORCH;
    public static Block SACRED_BARRIER;
    public static Block SACRED_BARRIER_STONE;
    public static Block SACRED_BARRIER_BRICK;
    public static Block SACRED_BARRIER_SMOOTH;

    // Void Realm dimension blocks
    public static Block VOID_STONE;
    public static Block CORRUPTION_FLESH;
    public static Block OBSIDIAN_CRUST;
    public static Block TWISTED_LOG;
    public static Block BLIGHTED_GRASS;

    // Méthode appelée par la classe principale. Nom aligné sur CorruptionMod.java
    public static void register() {
        // Source / core corruption block
    CORRUPTION_BLOCK = Registry.register(Registries.BLOCK, Identifier.of(CorruptionMod.MOD_ID, "corruption_block"), new CorruptionBlock(AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).strength(3.0f, 6.0f)));

        // Converted block variants
    CORRUPTED_GRASS = Registry.register(Registries.BLOCK, Identifier.of(CorruptionMod.MOD_ID, "corrupted_grass"), new CorruptedGrassBlock());
    // Corrupted dirt variant used by CorruptionBlock
    CORRUPTED_DIRT = Registry.register(Registries.BLOCK, Identifier.of(CorruptionMod.MOD_ID, "corrupted_dirt"), new com.corruptionmod.block.CorruptedDirtBlock());
        CORRUPTED_STONE = Registry.register(Registries.BLOCK, Identifier.of(CorruptionMod.MOD_ID, "corrupted_stone"), new CorruptedStoneBlock());
        ROTTED_WOOD = Registry.register(Registries.BLOCK, Identifier.of(CorruptionMod.MOD_ID, "rotted_wood"), new RottedWoodBlock());
        CORRUPTED_SAND = Registry.register(Registries.BLOCK, Identifier.of(CorruptionMod.MOD_ID, "corrupted_sand"), new CorruptedSandBlock());
        TAINTED_WATER = Registry.register(Registries.BLOCK, Identifier.of(CorruptionMod.MOD_ID, "tainted_water"), new TaintedWaterBlock());
        WITHERED_LEAVES = Registry.register(Registries.BLOCK, Identifier.of(CorruptionMod.MOD_ID, "withered_leaves"), new com.corruptionmod.block.WitheredLeavesBlock());

        // Utility blocks
        CORRUPTION_ALTAR = Registry.register(Registries.BLOCK, Identifier.of(CorruptionMod.MOD_ID, "corruption_altar"), new CorruptionAltarBlock());
        VOID_PORTAL_FRAME = Registry.register(Registries.BLOCK, Identifier.of(CorruptionMod.MOD_ID, "void_portal_frame"), new VoidPortalBlock());
        PURIFICATION_CRYSTAL = Registry.register(Registries.BLOCK, Identifier.of(CorruptionMod.MOD_ID, "purification_crystal"), new com.corruptionmod.block.PurificationCrystalBlock());

        // Void Realm dimension blocks
        VOID_STONE = Registry.register(Registries.BLOCK, Identifier.of(CorruptionMod.MOD_ID, "void_stone"), new VoidStoneBlock());
        CORRUPTION_FLESH = Registry.register(Registries.BLOCK, Identifier.of(CorruptionMod.MOD_ID, "corruption_flesh"), new CorruptionFleshBlock());
        OBSIDIAN_CRUST = Registry.register(Registries.BLOCK, Identifier.of(CorruptionMod.MOD_ID, "obsidian_crust"), new ObsidianCrustBlock());
        TWISTED_LOG = Registry.register(Registries.BLOCK, Identifier.of(CorruptionMod.MOD_ID, "twisted_log"), new TwistedLogBlock());
        BLIGHTED_GRASS = Registry.register(Registries.BLOCK, Identifier.of(CorruptionMod.MOD_ID, "blighted_grass"), new BlightedGrassBlock());
    }
}
