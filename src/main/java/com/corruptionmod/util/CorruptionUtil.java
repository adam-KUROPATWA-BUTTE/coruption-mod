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
    }
}
