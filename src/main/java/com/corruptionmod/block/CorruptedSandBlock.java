package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;

/**
 * Sable corrompu.
 */
public class CorruptedSandBlock extends CorruptionBlock {
    public CorruptedSandBlock() {
        super(Settings.of(Material.AGGREGATE).strength(0.5f));
    }
}
