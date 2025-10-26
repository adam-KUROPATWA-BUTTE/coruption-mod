package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;

/**
 * Bloc de cadre du portail du Néant.
 */
public class VoidPortalBlock extends Block {
    public VoidPortalBlock() {
        super(Settings.of(Material.STONE).strength(5.0f, 10.0f));
    }
}
