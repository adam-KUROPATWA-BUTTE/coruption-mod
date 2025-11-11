package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;

/**
 * Bloc de cadre du portail du Néant.
 */
public class VoidPortalBlock extends Block {
    public VoidPortalBlock() {
        super(AbstractBlock.Settings.create().mapColor(MapColor.BLACK).strength(5.0f, 10.0f).requiresTool());
    }
}
