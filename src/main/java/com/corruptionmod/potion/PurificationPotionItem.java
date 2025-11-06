package com.corruptionmod.potion;

import com.corruptionmod.ModBlocks;
import com.corruptionmod.ModEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Custom potion item that can be drunk to remove corruption from player
 * or thrown to purify corrupted blocks in an area.
 */
public class PurificationPotionItem extends PotionItem {
    private final int tier; // 1, 2, or 3 for different effectiveness
    
    public PurificationPotionItem(Settings settings, int tier) {
        super(settings);
        this.tier = tier;
    }
    
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        
        if (!world.isClient) {
            // Apply purification effect to player
            int duration = 20 * 30 * tier; // 30 seconds per tier
            int amplifier = tier - 1;
            user.addStatusEffect(new StatusEffectInstance(ModEffects.PURIFICATION, duration, amplifier));
            
            if (!user.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        
        return TypedActionResult.success(stack, world.isClient());
    }
    
    /**
     * Called when potion is thrown and hits something.
     * Purifies blocks in an area.
     */
    public static void onThrownImpact(World world, BlockPos centerPos, int tier) {
        if (world.isClient || !(world instanceof ServerWorld serverWorld)) {
            return;
        }
        
        int radius = 2 + tier; // Radius increases with tier
        int maxBlocks = 10 * tier; // More blocks purified with higher tier
        int purified = 0;
        
        for (int dx = -radius; dx <= radius && purified < maxBlocks; dx++) {
            for (int dz = -radius; dz <= radius && purified < maxBlocks; dz++) {
                for (int dy = -2; dy <= 2 && purified < maxBlocks; dy++) {
                    BlockPos pos = centerPos.add(dx, dy, dz);
                    BlockState state = world.getBlockState(pos);
                    Block block = state.getBlock();
                    
                    // Check if block is corrupted
                    BlockState cleanedState = null;
                    if (block == ModBlocks.CORRUPTED_GRASS) {
                        cleanedState = Blocks.GRASS_BLOCK.getDefaultState();
                    } else if (block == ModBlocks.CORRUPTED_DIRT) {
                        cleanedState = Blocks.DIRT.getDefaultState();
                    } else if (block == ModBlocks.CORRUPTED_STONE) {
                        cleanedState = Blocks.STONE.getDefaultState();
                    } else if (block == ModBlocks.ROTTED_WOOD) {
                        cleanedState = Blocks.OAK_LOG.getDefaultState();
                    } else if (block == ModBlocks.CORRUPTED_SAND) {
                        cleanedState = Blocks.SAND.getDefaultState();
                    } else if (block == ModBlocks.TAINTED_WATER) {
                        cleanedState = Blocks.WATER.getDefaultState();
                    } else if (block == ModBlocks.WITHERED_LEAVES) {
                        cleanedState = Blocks.OAK_LEAVES.getDefaultState();
                    } else if (block == ModBlocks.CORRUPTION_BLOCK) {
                        cleanedState = Blocks.DIRT.getDefaultState();
                    }
                    
                    if (cleanedState != null) {
                        world.setBlockState(pos, cleanedState);
                        // Spawn particles
                        serverWorld.spawnParticles(ParticleTypes.HAPPY_VILLAGER,
                            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            5, 0.3, 0.3, 0.3, 0.0);
                        purified++;
                    }
                }
            }
        }
    }
    
    public int getTier() {
        return tier;
    }
}
