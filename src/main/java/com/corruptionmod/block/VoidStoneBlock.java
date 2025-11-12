package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Base stone block for the Void Realm dimension.
 * Emits occasional void particles.
 */
public class VoidStoneBlock extends Block {
    public VoidStoneBlock() {
        super(AbstractBlock.Settings.create()
                .mapColor(MapColor.BLACK)
                .strength(2.5f, 12.0f)
                .requiresTool()
                .luminance(state -> 1));
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(10) == 0) {
            world.addParticle(
                ParticleTypes.PORTAL,
                pos.getX() + random.nextDouble(),
                pos.getY() + random.nextDouble(),
                pos.getZ() + random.nextDouble(),
                0.0, 0.0, 0.0
            );
        }
    }
}
