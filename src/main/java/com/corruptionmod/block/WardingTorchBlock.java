package com.corruptionmod.block;

import com.corruptionmod.event.PurificationManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Special torch variant that slows corruption spread within a small radius.
 * Multiple torches can stack their effects for stronger protection.
 */
public class WardingTorchBlock extends TorchBlock {
    private static final int PROTECTION_RADIUS = 8; // Smaller than crystal
    
    public WardingTorchBlock(Settings settings) {
        super(ParticleTypes.FLAME, settings);
    }
    
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, net.minecraft.entity.LivingEntity placer, net.minecraft.item.ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient && world instanceof ServerWorld sw) {
            PurificationManager.addWardingTorch(sw, pos);
        }
    }
    
    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, net.minecraft.entity.player.PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if (!world.isClient && world instanceof ServerWorld sw) {
            PurificationManager.removeWardingTorch(sw, pos);
        }
    }
    
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        // Add purple/golden particles to distinguish from normal torch
        double x = (double)pos.getX() + 0.5;
        double y = (double)pos.getY() + 0.7;
        double z = (double)pos.getZ() + 0.5;
        world.addParticle(ParticleTypes.FLAME, x, y, z, 0.0, 0.0, 0.0);
        
        // Occasionally add enchant glint particle
        if (random.nextInt(20) == 0) {
            world.addParticle(ParticleTypes.ENCHANT, x, y, z, 0.0, 0.02, 0.0);
        }
    }
    
    public static int getProtectionRadius() {
        return PROTECTION_RADIUS;
    }
}
