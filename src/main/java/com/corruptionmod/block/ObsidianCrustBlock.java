package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Volcanic-like obsidian block found in the Obsidian Wastes biome.
 * Emits smoke particles.
 */
public class ObsidianCrustBlock extends Block {
    public ObsidianCrustBlock() {
        super(AbstractBlock.Settings.create()
                .mapColor(MapColor.BLACK)
                .strength(50.0f, 1200.0f)
                .requiresTool()
                .sounds(BlockSoundGroup.STONE)
                .luminance(state -> 3));
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(15) == 0) {
            world.addParticle(
                ParticleTypes.SMOKE,
                pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.5,
                pos.getY() + 1.0,
                pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.5,
                0.0, 0.05, 0.0
            );
        }
    }
}
