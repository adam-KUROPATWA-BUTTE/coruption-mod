package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;

/**
 * Sable corrompu.
 */
public class CorruptedSandBlock extends CorruptionBlock {
    public CorruptedSandBlock() {
        super(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(0.5f));
    }
}
