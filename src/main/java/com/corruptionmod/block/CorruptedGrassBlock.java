package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;

/**
 * Bloc d'herbe corrompue : variation visuelle, pas de logique lourde ici (prototype).
 */
public class CorruptedGrassBlock extends CorruptionBlock {
    public CorruptedGrassBlock() {
        super(Settings.of(Material.SOIL).strength(0.6f));
    }
}
