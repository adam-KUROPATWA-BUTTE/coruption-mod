package com.corruptionmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Organic corrupted flesh block found in the Void Realm.
 * Slows entities walking on it and emits disturbing particles.
 */
public class CorruptionFleshBlock extends Block {
    public CorruptionFleshBlock() {
        super(Settings.of(Material.ORGANIC_PRODUCT)
                .strength(1.0f, 3.0f)
                .sounds(BlockSoundGroup.HONEY)
                .slipperiness(0.6f));
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LivingEntity) {
            entity.setVelocity(entity.getVelocity().multiply(0.7, 1.0, 0.7));
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(20) == 0) {
            world.addParticle(
                ParticleTypes.CRIMSON_SPORE,
                pos.getX() + random.nextDouble(),
                pos.getY() + 1.0,
                pos.getZ() + random.nextDouble(),
                0.0, 0.02, 0.0
            );
        }
    }
}
