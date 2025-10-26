package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;

/**
 * Bloc de pierre corrompue (visuel + pulsation possible).
 */
public class CorruptedStoneBlock extends CorruptionBlock {
    public CorruptedStoneBlock() {
        super(Settings.of(Material.STONE).strength(3.0f, 6.0f));
    }
}
