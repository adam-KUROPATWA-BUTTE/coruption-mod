package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;

/**
 * Autel d'invocation pour Xynor.
 */
public class CorruptionAltarBlock extends Block {
    public CorruptionAltarBlock() {
        super(Settings.of(Material.STONE).strength(4.0f, 8.0f));
    }
}
