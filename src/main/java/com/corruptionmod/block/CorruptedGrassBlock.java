package com.corruptionmod.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Corrupted grass block with purple-gray texture and random tick spreading.
 * Spreads corruption to nearby grass/dirt blocks.
 */
public class CorruptedGrassBlock extends CorruptionBlock {
    public CorruptedGrassBlock() {
        super(AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).strength(0.6f).ticksRandomly());
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // Call parent's random tick for standard corruption spreading
        super.randomTick(state, world, pos, random);
        
        // Additional grass-specific spreading: can spread horizontally to adjacent grass blocks
        if (random.nextFloat() < 0.3f) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dz == 0) continue;
                    
                    BlockPos targetPos = pos.add(dx, 0, dz);
                    BlockState targetState = world.getBlockState(targetPos);
                    String key = targetState.getBlock().getTranslationKey().toLowerCase();
                    
                    // Spread to grass or dirt blocks nearby
                    if (key.contains("grass_block") || key.contains("dirt")) {
                        if (random.nextFloat() < 0.15f) {
                            world.setBlockState(targetPos, this.getDefaultState());
                            spawnSpreadParticles(world, targetPos);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        // Purple-gray particles occasionally
        if (random.nextInt(15) == 0) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + 1.0;
            double z = pos.getZ() + random.nextDouble();
            world.addParticle(ParticleTypes.PORTAL, x, y, z, 0.0, 0.05, 0.0);
        }
    }

    private void spawnSpreadParticles(World world, BlockPos pos) {
        Random random = world.getRandom();
        for (int i = 0; i < 4; i++) {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.6;
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.6;
            world.addParticle(ParticleTypes.PORTAL, x, y, z, 0.0, 0.03, 0.0);
        }
    }
}
