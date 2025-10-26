package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;

/**
 * Eau souillée par la corruption (prototype). Inflige des dégâts au contact.
 */
public class TaintedWaterBlock extends CorruptionBlock {
    public TaintedWaterBlock() {
        super(Settings.of(Material.WATER).noCollision().strength(100f));
    }
}
