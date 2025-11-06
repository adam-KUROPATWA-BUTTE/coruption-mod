package com.corruptionmod.event;

import com.corruptionmod.ModBlocks;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Manager for Purification Crystals and Warding Torches.
 * Crystals restore corrupted blocks slowly in a 32 block radius.
 * Warding Torches provide smaller protection zones (8 blocks) that can stack.
 */
public class PurificationManager {
    private static final Map<ServerWorld, Set<BlockPos>> crystals = new HashMap<>();
    private static final Map<ServerWorld, Set<BlockPos>> wardingTorches = new HashMap<>();
    private static final int TICKS_PER_REVERT = 20 * 10; // 10s
    private static final int PURIFICATION_RADIUS = 32; // Radius where corruption is blocked
    private static long tickCounter = 0;

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(PurificationManager::onWorldTick);
    }

    private static void onWorldTick(ServerWorld world) {
        tickCounter++;
        if (tickCounter % TICKS_PER_REVERT != 0) return;

        Set<BlockPos> set = crystals.get(world);
        if (set == null || set.isEmpty()) return;

        // For each crystal, find one corrupted block in radius and revert it
        for (BlockPos crystalPos : new HashSet<>(set)) {
            BlockPos found = findNearbyCorruption(world, crystalPos, PURIFICATION_RADIUS);
            if (found != null) {
                // Replace with appropriate clean block (prototype: dirt)
                world.setBlockState(found, net.minecraft.block.Blocks.DIRT.getDefaultState());
                world.syncWorldEvent(2001, found, net.minecraft.block.Block.getRawIdFromState(ModBlocks.CORRUPTION_BLOCK.getDefaultState()));
            }
        }
    }

    private static BlockPos findNearbyCorruption(ServerWorld world, BlockPos center, int radius) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                for (int dy = -4; dy <= 4; dy++) {
                    BlockPos p = center.add(dx, dy, dz);
                    if (world.getBlockState(p).getBlock() == ModBlocks.CORRUPTION_BLOCK) return p;
                }
            }
        }
        return null;
    }
    
    /**
     * Check if a position is within a purified zone protected by a crystal.
     * This prevents corruption from spreading into protected areas.
     * @param world The world
     * @param pos The position to check
     * @return true if the position is protected by a purification crystal
     */
    public static boolean isInPurifiedZone(ServerWorld world, BlockPos pos) {
        Set<BlockPos> set = crystals.get(world);
        if (set == null || set.isEmpty()) return false;
        
        // Check if position is within purification radius of any crystal
        for (BlockPos crystalPos : set) {
            double distanceSquared = pos.getSquaredDistance(crystalPos);
            if (distanceSquared <= PURIFICATION_RADIUS * PURIFICATION_RADIUS) {
                return true;
            }
        }
        return false;
    }

    public static void addCrystal(ServerWorld world, BlockPos pos) {
        crystals.computeIfAbsent(world, k -> new HashSet<>()).add(pos);
    }

    public static void removeCrystal(ServerWorld world, BlockPos pos) {
        Set<BlockPos> set = crystals.get(world);
        if (set != null) set.remove(pos);
    }
    
    public static void addWardingTorch(ServerWorld world, BlockPos pos) {
        wardingTorches.computeIfAbsent(world, k -> new HashSet<>()).add(pos);
    }
    
    public static void removeWardingTorch(ServerWorld world, BlockPos pos) {
        Set<BlockPos> set = wardingTorches.get(world);
        if (set != null) set.remove(pos);
    }
    
    /**
     * Checks if a position is protected by purification crystals.
     */
    public static boolean isProtectedByCrystal(ServerWorld world, BlockPos pos) {
        Set<BlockPos> set = crystals.get(world);
        if (set == null || set.isEmpty()) return false;
        
        for (BlockPos crystalPos : set) {
            if (crystalPos.isWithinDistance(pos, 32)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Calculates the warding torch protection level at a position.
     * Multiple torches within range stack their effects.
     * Returns 0.0 (no protection) to 1.0 (full protection).
     */
    public static float getWardingProtection(ServerWorld world, BlockPos pos) {
        Set<BlockPos> set = wardingTorches.get(world);
        if (set == null || set.isEmpty()) return 0.0f;
        
        int nearbyTorches = 0;
        for (BlockPos torchPos : set) {
            if (torchPos.isWithinDistance(pos, 8)) {
                nearbyTorches++;
            }
        }
        
        // Each torch provides 25% protection, up to 100% with 4+ torches
        return Math.min(1.0f, nearbyTorches * 0.25f);
    }
}
