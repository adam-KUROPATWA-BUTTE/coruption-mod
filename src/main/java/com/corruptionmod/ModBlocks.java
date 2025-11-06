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
import com.corruptionmod.block.WardingTorchBlock;
import com.corruptionmod.block.WardingWallTorchBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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

    // Méthode appelée par la classe principale. Nom aligné sur CorruptionMod.java
    public static void register() {
        // Source / core corruption block
    CORRUPTION_BLOCK = Registry.register(Registry.BLOCK, new Identifier(CorruptionMod.MOD_ID, "corruption_block"), new CorruptionBlock(Block.Settings.of(Material.STONE).strength(3.0f, 6.0f)));

        // Converted block variants
    CORRUPTED_GRASS = Registry.register(Registry.BLOCK, new Identifier(CorruptionMod.MOD_ID, "corrupted_grass"), new CorruptedGrassBlock());
    // Corrupted dirt variant used by CorruptionBlock
    CORRUPTED_DIRT = Registry.register(Registry.BLOCK, new Identifier(CorruptionMod.MOD_ID, "corrupted_dirt"), new com.corruptionmod.block.CorruptedDirtBlock());
        CORRUPTED_STONE = Registry.register(Registry.BLOCK, new Identifier(CorruptionMod.MOD_ID, "corrupted_stone"), new CorruptedStoneBlock());
        ROTTED_WOOD = Registry.register(Registry.BLOCK, new Identifier(CorruptionMod.MOD_ID, "rotted_wood"), new RottedWoodBlock());
        CORRUPTED_SAND = Registry.register(Registry.BLOCK, new Identifier(CorruptionMod.MOD_ID, "corrupted_sand"), new CorruptedSandBlock());
        TAINTED_WATER = Registry.register(Registry.BLOCK, new Identifier(CorruptionMod.MOD_ID, "tainted_water"), new TaintedWaterBlock());
        WITHERED_LEAVES = Registry.register(Registry.BLOCK, new Identifier(CorruptionMod.MOD_ID, "withered_leaves"), new com.corruptionmod.block.WitheredLeavesBlock());

        // Utility blocks
        CORRUPTION_ALTAR = Registry.register(Registry.BLOCK, new Identifier(CorruptionMod.MOD_ID, "corruption_altar"), new CorruptionAltarBlock());
        VOID_PORTAL_FRAME = Registry.register(Registry.BLOCK, new Identifier(CorruptionMod.MOD_ID, "void_portal_frame"), new VoidPortalBlock());
        PURIFICATION_CRYSTAL = Registry.register(Registry.BLOCK, new Identifier(CorruptionMod.MOD_ID, "purification_crystal"), new com.corruptionmod.block.PurificationCrystalBlock());
        
        // Purification system blocks
        CLEANSING_ALTAR = Registry.register(Registry.BLOCK, new Identifier(CorruptionMod.MOD_ID, "cleansing_altar"), new CleansingAltarBlock(Block.Settings.of(Material.STONE).strength(3.0f).luminance(7)));
        WARDING_TORCH = Registry.register(Registry.BLOCK, new Identifier(CorruptionMod.MOD_ID, "warding_torch"), new WardingTorchBlock(Block.Settings.of(Material.DECORATION).noCollision().strength(0.0f).luminance(14)));
        WARDING_WALL_TORCH = Registry.register(Registry.BLOCK, new Identifier(CorruptionMod.MOD_ID, "warding_wall_torch"), new WardingWallTorchBlock(Block.Settings.of(Material.DECORATION).noCollision().strength(0.0f).luminance(14)));
        SACRED_BARRIER = Registry.register(Registry.BLOCK, new Identifier(CorruptionMod.MOD_ID, "sacred_barrier"), new SacredBarrierBlock(Block.Settings.of(Material.STONE).strength(5.0f, 1200.0f).luminance(5)));
        SACRED_BARRIER_STONE = Registry.register(Registry.BLOCK, new Identifier(CorruptionMod.MOD_ID, "sacred_barrier_stone"), new SacredBarrierBlock(Block.Settings.of(Material.STONE).strength(5.0f, 1200.0f).luminance(5)));
        SACRED_BARRIER_BRICK = Registry.register(Registry.BLOCK, new Identifier(CorruptionMod.MOD_ID, "sacred_barrier_brick"), new SacredBarrierBlock(Block.Settings.of(Material.STONE).strength(5.0f, 1200.0f).luminance(5)));
        SACRED_BARRIER_SMOOTH = Registry.register(Registry.BLOCK, new Identifier(CorruptionMod.MOD_ID, "sacred_barrier_smooth"), new SacredBarrierBlock(Block.Settings.of(Material.STONE).strength(5.0f, 1200.0f).luminance(5)));
    }
}
