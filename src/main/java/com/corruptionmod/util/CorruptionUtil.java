package com.corruptionmod.util;

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
        String key = state.getBlock().getTranslationKey().toLowerCase();
        for (String keyword : CORRUPTIBLE_BLOCK_KEYWORDS) {
            if (key.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check if a block keyword represents a natural corruptible block type.
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
