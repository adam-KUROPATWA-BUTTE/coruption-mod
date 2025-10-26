package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;

/**
 * Bois pourri : casse plus facilement.
 */
public class RottedWoodBlock extends CorruptionBlock {
    public RottedWoodBlock() {
        super(Settings.of(Material.WOOD).strength(1.5f, 3.0f));
    }
}
