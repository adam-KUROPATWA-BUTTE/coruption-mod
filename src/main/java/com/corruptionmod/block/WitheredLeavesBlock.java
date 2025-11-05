package com.corruptionmod.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Dark brown withered leaves with particle effects.
 * Extends CorruptionBlock to inherit corruption spreading capabilities.
 */
public class WitheredLeavesBlock extends CorruptionBlock {
    public WitheredLeavesBlock() {
        super(Settings.of(Material.LEAVES)
                .strength(0.2f)
                .nonOpaque()
                .allowsSpawning((state, world, pos, type) -> false)
                .suffocates((state, world, pos) -> false)
                .blockVision((state, world, pos) -> false));
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        // Spawn dark particles around withered leaves
        if (random.nextInt(10) == 0) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + random.nextDouble();
            double z = pos.getZ() + random.nextDouble();
            
            // Use ash particles (smoke) to represent withering
            world.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, -0.05, 0.0);
        }
        
        // Occasionally spawn a portal particle for corruption effect
        if (random.nextInt(20) == 0) {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.8;
            double y = pos.getY() + random.nextDouble();
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.8;
            world.addParticle(ParticleTypes.PORTAL, x, y, z, 0.0, 0.02, 0.0);
        }
    }
    
    private float corruptionChanceFor(BlockState state) {
        String key = state.getBlock().getTranslationKey().toLowerCase();
        if (key.contains("leaves")) return 0.3f;
        if (key.contains("log") || key.contains("wood")) return 0.15f;
        return 0.05f;
    }
}
