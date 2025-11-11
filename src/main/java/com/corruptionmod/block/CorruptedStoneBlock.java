package com.corruptionmod.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Corrupted stone block with pulsing animation capability.
 * Displays periodic particle effects to simulate pulsing.
 */
public class CorruptedStoneBlock extends CorruptionBlock {
    public CorruptedStoneBlock() {
        super(AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).strength(3.0f, 6.0f).requiresTool());
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        // Pulsing effect: create a wave of particles periodically
        // Using world time to create a synchronized pulse effect
        long time = world.getTime();
        int pulsePhase = (int)(time % 40); // 2 second cycle (40 ticks)
        
        // Pulse occurs between ticks 0-10 of the cycle
        if (pulsePhase < 10) {
            // Increased particle spawn rate during pulse
            if (random.nextInt(3) == 0) {
                double x = pos.getX() + random.nextDouble();
                double y = pos.getY() + random.nextDouble();
                double z = pos.getZ() + random.nextDouble();
                
                // Pulse intensity based on phase (0 = strongest, 10 = weakest)
                double intensity = (10 - pulsePhase) / 10.0;
                
                // Portal particles for corruption effect
                world.addParticle(ParticleTypes.PORTAL, x, y, z, 
                    (random.nextDouble() - 0.5) * 0.02 * intensity,
                    random.nextDouble() * 0.05 * intensity,
                    (random.nextDouble() - 0.5) * 0.02 * intensity);
            }
        } else {
            // Base corruption particles outside of pulse
            if (random.nextInt(10) == 0) {
                double x = pos.getX() + random.nextDouble();
                double y = pos.getY() + random.nextDouble();
                double z = pos.getZ() + random.nextDouble();
                world.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.01, 0.0);
            }
        }
    }
}
