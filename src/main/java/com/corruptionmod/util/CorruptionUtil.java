package com.corruptionmod.util;

import com.corruptionmod.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

/**
 * Utility class for corruption-related block operations.
 * Centralizes logic for identifying corrupted blocks and their clean variants.
 */
public class CorruptionUtil {
    
    /**
     * Checks if a block is corrupted.
     */
    public static boolean isCorruptedBlock(BlockState state) {
        Block block = state.getBlock();
        return block == ModBlocks.CORRUPTION_BLOCK ||
               block == ModBlocks.CORRUPTED_GRASS ||
               block == ModBlocks.CORRUPTED_DIRT ||
               block == ModBlocks.CORRUPTED_STONE ||
               block == ModBlocks.ROTTED_WOOD ||
               block == ModBlocks.CORRUPTED_SAND ||
               block == ModBlocks.TAINTED_WATER ||
               block == ModBlocks.WITHERED_LEAVES;
    }
    
    /**
     * Returns the cleaned variant of a corrupted block.
     * If the block is not corrupted, returns the original state.
     */
    public static BlockState getCleanedVariant(BlockState corrupted) {
        Block block = corrupted.getBlock();
        
        if (block == ModBlocks.CORRUPTED_GRASS) return Blocks.GRASS_BLOCK.getDefaultState();
        if (block == ModBlocks.CORRUPTED_DIRT) return Blocks.DIRT.getDefaultState();
        if (block == ModBlocks.CORRUPTED_STONE) return Blocks.STONE.getDefaultState();
        if (block == ModBlocks.ROTTED_WOOD) return Blocks.OAK_LOG.getDefaultState();
        if (block == ModBlocks.CORRUPTED_SAND) return Blocks.SAND.getDefaultState();
        if (block == ModBlocks.TAINTED_WATER) return Blocks.WATER.getDefaultState();
        if (block == ModBlocks.WITHERED_LEAVES) return Blocks.OAK_LEAVES.getDefaultState();
        if (block == ModBlocks.CORRUPTION_BLOCK) return Blocks.DIRT.getDefaultState();
        
        return corrupted;
    }
    
    /**
     * Checks if a block can be corrupted based on its type.
     * Sacred barriers, obsidian, and bedrock cannot be corrupted.
     */
    public static boolean canBeCorrupted(Block block) {
        // Sacred Barrier blocks cannot be corrupted
        if (com.corruptionmod.block.SacredBarrierBlock.isBarrier(block)) {
            return false;
        }
        
        // Prevent corruption through obsidian or bedrock
        if (block == Blocks.OBSIDIAN || block == Blocks.BEDROCK) {
            return false;
        }
        
        // By default, blocks can be corrupted
        return true;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for corruption-related block checks and validations.
 */
public class CorruptionUtil {
    
    // Natural block types that can be corrupted
    private static final Set<String> CORRUPTIBLE_BLOCK_KEYWORDS = new HashSet<>();
    
    static {
        CORRUPTIBLE_BLOCK_KEYWORDS.add("grass");
        CORRUPTIBLE_BLOCK_KEYWORDS.add("dirt");
        CORRUPTIBLE_BLOCK_KEYWORDS.add("stone");
        CORRUPTIBLE_BLOCK_KEYWORDS.add("sand");
        CORRUPTIBLE_BLOCK_KEYWORDS.add("gravel");
        CORRUPTIBLE_BLOCK_KEYWORDS.add("log");
        CORRUPTIBLE_BLOCK_KEYWORDS.add("wood");
        CORRUPTIBLE_BLOCK_KEYWORDS.add("water");
        CORRUPTIBLE_BLOCK_KEYWORDS.add("leaves");
    }
    
    /**
     * Check if a block is a natural block that can be corrupted.
     * @param state The block state to check
     * @return true if the block is a natural block type
     */
    public static boolean isNaturalBlock(BlockState state) {
        return isNaturalBlockByName(state.getBlock().getTranslationKey().toLowerCase());
    }
    
    /**
     * Check if a block keyword represents a natural corruptible block type.
     * Uses efficient HashSet contains checks for better performance.
     * @param blockName The block translation key (lowercased)
     * @return true if it matches a corruptible block type
     */
    public static boolean isNaturalBlockByName(String blockName) {
        for (String keyword : CORRUPTIBLE_BLOCK_KEYWORDS) {
            if (blockName.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}
