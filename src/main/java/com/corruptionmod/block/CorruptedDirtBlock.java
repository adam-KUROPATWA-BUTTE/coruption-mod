package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;

/**
 * Dirt corrompu (variant pour conversion).
 */
public class CorruptedDirtBlock extends CorruptionBlock {
    public CorruptedDirtBlock() {
        super(AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).strength(0.5f));
    }
}
