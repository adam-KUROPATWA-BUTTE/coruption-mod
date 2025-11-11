package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;

/**
 * Autel d'invocation pour Xynor.
 */
public class CorruptionAltarBlock extends Block {
    public CorruptionAltarBlock() {
        super(AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).strength(4.0f, 8.0f).requiresTool());
    }
}
