package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.sound.BlockSoundGroup;

/**
 * Corrupted twisted wood log for the Writhing Forest biome.
 */
public class TwistedLogBlock extends PillarBlock {
    public TwistedLogBlock() {
        super(Settings.of(Material.WOOD)
                .strength(2.0f)
                .sounds(BlockSoundGroup.WOOD));
    }
}
