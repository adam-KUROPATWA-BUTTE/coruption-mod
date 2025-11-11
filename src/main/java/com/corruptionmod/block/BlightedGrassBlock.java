package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Corrupted grass block for the Blighted Plains biome.
 * Similar to corrupted grass but specific to the dimension.
 */
public class BlightedGrassBlock extends Block {
    public BlightedGrassBlock() {
        super(AbstractBlock.Settings.create()
                .mapColor(MapColor.PALE_GREEN)
                .strength(0.6f)
                .sounds(BlockSoundGroup.GRASS)
                .ticksRandomly());
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(30) == 0) {
            world.addParticle(
                ParticleTypes.MYCELIUM,
                pos.getX() + random.nextDouble(),
                pos.getY() + 1.0,
                pos.getZ() + random.nextDouble(),
                0.0, 0.0, 0.0
            );
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // Spread corruption to nearby blocks occasionally
        if (random.nextInt(5) == 0) {
            BlockPos targetPos = pos.add(
                random.nextInt(3) - 1,
                random.nextInt(3) - 1,
                random.nextInt(3) - 1
            );
            // Could implement spreading logic here if needed
        }
    }
}
