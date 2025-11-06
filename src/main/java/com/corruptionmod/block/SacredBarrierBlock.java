package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Sacred Barrier Block - prevents corruption from spreading through it.
 * Used to create safe zones and protect areas from corruption.
 */
public class SacredBarrierBlock extends Block {
    
    public SacredBarrierBlock(Settings settings) {
        super(settings);
    }
    
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        // Occasionally spawn protective particles
        if (random.nextInt(10) == 0) {
            double x = (double)pos.getX() + random.nextDouble();
            double y = (double)pos.getY() + random.nextDouble();
            double z = (double)pos.getZ() + random.nextDouble();
            world.addParticle(ParticleTypes.END_ROD, x, y, z, 0.0, 0.02, 0.0);
        }
    }
    
    /**
     * Returns true if this block type can stop corruption spread.
     * Used by WorldCorruptionTicker to check barriers.
     */
    public static boolean isBarrier(Block block) {
        return block instanceof SacredBarrierBlock;
    }
}
