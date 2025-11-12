package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;

/**
 * Bois pourri : casse plus facilement.
 */
public class RottedWoodBlock extends CorruptionBlock {
    public RottedWoodBlock() {
        super(AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).strength(1.5f, 3.0f));
    }
}
