package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.sound.BlockSoundGroup;

/**
 * Corrupted twisted wood log for the Writhing Forest biome.
 */
public class TwistedLogBlock extends PillarBlock {
    public TwistedLogBlock() {
        super(AbstractBlock.Settings.create()
                .mapColor(MapColor.OAK_TAN)
                .strength(2.0f)
                .sounds(BlockSoundGroup.WOOD));
    }
}
