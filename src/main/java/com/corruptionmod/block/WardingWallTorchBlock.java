package com.corruptionmod.block;

import com.corruptionmod.event.PurificationManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Wall-mounted variant of the Warding Torch.
 */
public class WardingWallTorchBlock extends WallTorchBlock {
    
    public WardingWallTorchBlock(Settings settings) {
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
        // Direction-based particle positioning for wall torch
        net.minecraft.util.math.Direction direction = state.get(FACING);
        double x = (double)pos.getX() + 0.5;
        double y = (double)pos.getY() + 0.7;
        double z = (double)pos.getZ() + 0.5;
        double offset = 0.27;
        
        double offsetX = (double)direction.getOffsetX() * offset;
        double offsetZ = (double)direction.getOffsetZ() * offset;
        
        world.addParticle(ParticleTypes.FLAME, x + offsetX, y, z + offsetZ, 0.0, 0.0, 0.0);
        
        // Occasionally add enchant glint particle
        if (random.nextInt(20) == 0) {
            world.addParticle(ParticleTypes.ENCHANT, x + offsetX, y, z + offsetZ, 0.0, 0.02, 0.0);
        }
    }
}
