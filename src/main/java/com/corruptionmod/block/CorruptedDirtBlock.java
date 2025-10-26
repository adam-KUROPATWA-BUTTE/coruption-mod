package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;

/**
 * Dirt corrompu (variant pour conversion).
 */
public class CorruptedDirtBlock extends CorruptionBlock {
    public CorruptedDirtBlock() {
        super(Settings.of(Material.SOIL).strength(0.5f));
    }
}
