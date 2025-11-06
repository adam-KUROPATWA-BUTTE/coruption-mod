package com.corruptionmod.event;

import com.corruptionmod.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

/**
 * Manages the cleansing ritual mechanics including structure validation,
 * item offerings, and purification effects.
 */
public class CleansingRitualManager {
    private static final Random RANDOM = new Random();
    private static final int PURIFICATION_RADIUS = 16;
    private static final int MAX_BLOCKS_PER_RITUAL = 25;
    
    // Ritual success chance (can be modified by offerings)
    private static final float BASE_SUCCESS_CHANCE = 0.7f;
    
    /**
     * Validates if the altar has a proper multi-block structure.
     * Required structure:
     * - Altar block in center
     * - 4 sacred barrier blocks at corners (3x3 pattern)
     * - Gold blocks at cardinal directions (optional, increases success rate)
     */
    public static boolean isValidStructure(ServerWorld world, BlockPos altarPos) {
        // Check for barrier blocks at corners of 3x3 area
        BlockPos[] cornerPositions = new BlockPos[] {
            altarPos.add(-1, -1, -1),
            altarPos.add(1, -1, -1),
            altarPos.add(-1, -1, 1),
            altarPos.add(1, -1, 1)
        };
        
        for (BlockPos corner : cornerPositions) {
            Block block = world.getBlockState(corner).getBlock();
            if (!(block instanceof com.corruptionmod.block.SacredBarrierBlock)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Calculates success chance based on structure enhancements.
     */
    private static float calculateSuccessChance(ServerWorld world, BlockPos altarPos) {
        float chance = BASE_SUCCESS_CHANCE;
        
        // Check for gold blocks at cardinal directions (increases success)
        BlockPos[] cardinalPositions = new BlockPos[] {
            altarPos.add(2, 0, 0),
            altarPos.add(-2, 0, 0),
            altarPos.add(0, 0, 2),
            altarPos.add(0, 0, -2)
        };
        
        int goldBlocks = 0;
        for (BlockPos cardinal : cardinalPositions) {
            if (world.getBlockState(cardinal).getBlock() == Blocks.GOLD_BLOCK) {
                goldBlocks++;
            }
        }
        
        // Each gold block adds 5% success chance
        chance += goldBlocks * 0.05f;
        
        return Math.min(1.0f, chance);
    }
    
    /**
     * Attempts to perform a cleansing ritual at the altar.
     * Requires offerings from the player and has a chance-based success system.
     */
    public static boolean attemptRitual(ServerWorld world, BlockPos altarPos, PlayerEntity player) {
        // Check if player has required offerings
        if (!hasRequiredOfferings(player)) {
            return false;
        }
        
        // Consume offerings
        consumeOfferings(player);
        
        // Calculate success chance
        float successChance = calculateSuccessChance(world, altarPos);
        boolean success = RANDOM.nextFloat() < successChance;
        
        if (success) {
            // Perform purification
            performPurification(world, altarPos);
            
            // Success effects
            spawnSuccessEffects(world, altarPos);
            world.playSound(null, altarPos, SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1.0f, 1.0f);
        } else {
            // Failure effects
            spawnFailureEffects(world, altarPos);
            world.playSound(null, altarPos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 0.5f);
        }
        
        return success;
    }
    
    /**
     * Checks if player has required ritual offerings.
     * Basic ritual requires: 4 diamonds, 2 gold ingots, 1 emerald
     */
    private static boolean hasRequiredOfferings(PlayerEntity player) {
        int diamonds = 0;
        int gold = 0;
        int emeralds = 0;
        
        for (ItemStack stack : player.getInventory().main) {
            if (stack.getItem() == Items.DIAMOND) {
                diamonds += stack.getCount();
            } else if (stack.getItem() == Items.GOLD_INGOT) {
                gold += stack.getCount();
            } else if (stack.getItem() == Items.EMERALD) {
                emeralds += stack.getCount();
            }
        }
        
        return diamonds >= 4 && gold >= 2 && emeralds >= 1;
    }
    
    /**
     * Consumes the required offerings from player inventory.
     */
    private static void consumeOfferings(PlayerEntity player) {
        int diamondsNeeded = 4;
        int goldNeeded = 2;
        int emeraldsNeeded = 1;
        
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            
            if (diamondsNeeded > 0 && stack.getItem() == Items.DIAMOND) {
                int toRemove = Math.min(diamondsNeeded, stack.getCount());
                stack.decrement(toRemove);
                diamondsNeeded -= toRemove;
            } else if (goldNeeded > 0 && stack.getItem() == Items.GOLD_INGOT) {
                int toRemove = Math.min(goldNeeded, stack.getCount());
                stack.decrement(toRemove);
                goldNeeded -= toRemove;
            } else if (emeraldsNeeded > 0 && stack.getItem() == Items.EMERALD) {
                int toRemove = Math.min(emeraldsNeeded, stack.getCount());
                stack.decrement(toRemove);
                emeraldsNeeded -= toRemove;
            }
        }
    }
    
    /**
     * Purifies corrupted blocks in the area around the altar.
     */
    private static void performPurification(ServerWorld world, BlockPos altarPos) {
        int purified = 0;
        
        for (int dx = -PURIFICATION_RADIUS; dx <= PURIFICATION_RADIUS && purified < MAX_BLOCKS_PER_RITUAL; dx++) {
            for (int dz = -PURIFICATION_RADIUS; dz <= PURIFICATION_RADIUS && purified < MAX_BLOCKS_PER_RITUAL; dz++) {
                for (int dy = -4; dy <= 4 && purified < MAX_BLOCKS_PER_RITUAL; dy++) {
                    BlockPos pos = altarPos.add(dx, dy, dz);
                    BlockState state = world.getBlockState(pos);
                    
                    if (isCorruptedBlock(state)) {
                        BlockState cleanedState = getCleanedVariant(state);
                        world.setBlockState(pos, cleanedState);
                        
                        // Particle effect at purified location
                        world.spawnParticles(ParticleTypes.HAPPY_VILLAGER, 
                            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 
                            5, 0.3, 0.3, 0.3, 0.0);
                        
                        purified++;
                    }
                }
            }
        }
    }
    
    /**
     * Checks if a block is corrupted.
     */
    private static boolean isCorruptedBlock(BlockState state) {
        Block block = state.getBlock();
        return block == ModBlocks.CORRUPTION_BLOCK ||
               block == ModBlocks.CORRUPTED_GRASS ||
               block == ModBlocks.CORRUPTED_DIRT ||
               block == ModBlocks.CORRUPTED_STONE ||
               block == ModBlocks.ROTTED_WOOD ||
               block == ModBlocks.CORRUPTED_SAND ||
               block == ModBlocks.TAINTED_WATER ||
               block == ModBlocks.WITHERED_LEAVES;
    }
    
    /**
     * Returns the cleaned variant of a corrupted block.
     */
    private static BlockState getCleanedVariant(BlockState corrupted) {
        Block block = corrupted.getBlock();
        
        if (block == ModBlocks.CORRUPTED_GRASS) return Blocks.GRASS_BLOCK.getDefaultState();
        if (block == ModBlocks.CORRUPTED_DIRT) return Blocks.DIRT.getDefaultState();
        if (block == ModBlocks.CORRUPTED_STONE) return Blocks.STONE.getDefaultState();
        if (block == ModBlocks.ROTTED_WOOD) return Blocks.OAK_LOG.getDefaultState();
        if (block == ModBlocks.CORRUPTED_SAND) return Blocks.SAND.getDefaultState();
        if (block == ModBlocks.TAINTED_WATER) return Blocks.WATER.getDefaultState();
        if (block == ModBlocks.WITHERED_LEAVES) return Blocks.OAK_LEAVES.getDefaultState();
        if (block == ModBlocks.CORRUPTION_BLOCK) return Blocks.DIRT.getDefaultState();
        
        return corrupted;
    }
    
    /**
     * Spawns particle effects for successful ritual.
     */
    private static void spawnSuccessEffects(ServerWorld world, BlockPos altarPos) {
        // Spiral of particles rising from altar
        for (int i = 0; i < 50; i++) {
            double angle = i * 0.3;
            double radius = 2.0;
            double x = altarPos.getX() + 0.5 + Math.cos(angle) * radius;
            double y = altarPos.getY() + 1.0 + i * 0.1;
            double z = altarPos.getZ() + 0.5 + Math.sin(angle) * radius;
            
            world.spawnParticles(ParticleTypes.END_ROD, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
            world.spawnParticles(ParticleTypes.ENCHANT, x, y, z, 2, 0.1, 0.1, 0.1, 0.0);
        }
    }
    
    /**
     * Spawns particle effects for failed ritual.
     */
    private static void spawnFailureEffects(ServerWorld world, BlockPos altarPos) {
        // Smoke and small explosion effect
        world.spawnParticles(ParticleTypes.LARGE_SMOKE, 
            altarPos.getX() + 0.5, altarPos.getY() + 1.0, altarPos.getZ() + 0.5, 
            20, 0.5, 0.5, 0.5, 0.02);
    }
}
