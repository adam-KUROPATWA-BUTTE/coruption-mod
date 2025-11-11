package com.corruptionmod.event;

import com.corruptionmod.ModBlocks;
import com.corruptionmod.block.SacredBarrierBlock;
import com.corruptionmod.util.CorruptionUtil;
import com.corruptionmod.util.PerformanceMonitor;
import com.corruptionmod.config.CorruptionConfig;
import net.minecraft.block.Block;
import com.corruptionmod.block.CorruptionBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Manages corruption spreading mechanics.
 * Uses chunk-based tracking with HashMap to efficiently track corruption progress
 * across loaded chunks. Spreads corruption at random intervals (20-40s) by sampling
 * positions in loaded chunks.
 */
public class WorldCorruptionTicker {
    // Configuration constants for tuning performance and behavior
    private static final Random RANDOM = new Random();

    // Next spread tick per dimension (world-level tracking)
    private static final Map<World, Long> nextSpreadTick = new HashMap<>();
    
    // Chunk-based corruption intensity tracking
    // Maps chunk position to corruption level (0.0 to 1.0)
    private static final Map<ChunkPos, Float> chunkCorruptionLevel = new HashMap<>();
    
    // Last processed time per chunk to avoid over-processing
    private static final Map<ChunkPos, Long> chunkLastProcessed = new HashMap<>();

    /**
     * Called from CorruptionMod via ServerTickEvents.END_WORLD_TICK
     */
    public static void tick(ServerWorld world) {
        if (world == null || world.isClient) return;

        long startTime = System.nanoTime();
        
        long time = world.getTime();
        long next = nextSpreadTick.computeIfAbsent(world, w -> time + randomInterval());

        if (time < next) return;

        // Execute corruption spread pass
        int blocksCorrupted = spreadCorruption(world);
        
        // Record performance metrics
        long elapsedTime = System.nanoTime() - startTime;
        PerformanceMonitor.recordTickTime(elapsedTime);
        PerformanceMonitor.recordSpreadEvent(blocksCorrupted);

        // Schedule next spread
        nextSpreadTick.put(world, time + randomInterval());
        
        // Cleanup old chunk data periodically
        long cleanupInterval = 20L * 60L * CorruptionConfig.chunkDataCleanupMinutes;
        if (time % cleanupInterval == 0) {
            cleanupChunkData(world, time);
        }
    }

    private static long randomInterval() {
        long minTicks = 20L * CorruptionConfig.corruptionSpreadMinSeconds;
        long maxTicks = 20L * CorruptionConfig.corruptionSpreadMaxSeconds;
        return minTicks + (long)(RANDOM.nextDouble() * (maxTicks - minTicks));
    }

    /**
     * Cleanup chunk tracking data for unloaded chunks
     */
    private static void cleanupChunkData(ServerWorld world, long currentTime) {
        long expiryTime = 20L * 60L * CorruptionConfig.chunkDataExpiryMinutes;
        
        chunkCorruptionLevel.entrySet().removeIf(entry -> {
            ChunkPos chunkPos = entry.getKey();
            // Remove if chunk hasn't been processed recently
            Long lastProcessed = chunkLastProcessed.get(chunkPos);
            if (lastProcessed != null && (currentTime - lastProcessed) > expiryTime) {
                PerformanceMonitor.clearChunkData(chunkPos);
                return true;
            }
            return false;
        });
        
        chunkLastProcessed.entrySet().removeIf(entry -> {
            Long lastProcessed = entry.getValue();
            return (currentTime - lastProcessed) > expiryTime;
        });
    }

    /**
     * Spread corruption logic: samples positions in loaded chunks
     * and attempts to spread corruption from source blocks.
     * Returns the number of blocks corrupted this pass.
     */
    private static int spreadCorruption(ServerWorld world) {
        if (!CorruptionConfig.enableOverworldCorruption && world.getRegistryKey() == World.OVERWORLD) {
            return 0;
        }
        
        int blocksCorrupted = 0;
        int samplesPerPlayer = CorruptionConfig.samplesPerPlayer;
        int sampleRadius = CorruptionConfig.sampleRadius;
        
        // Sample around connected players (loaded chunks)
        for (net.minecraft.entity.player.PlayerEntity player : world.getPlayers()) {
            int px = player.getBlockX();
            int pz = player.getBlockZ();
            
            // Track the chunk the player is in
            ChunkPos playerChunk = new ChunkPos(px >> 4, pz >> 4);
            chunkLastProcessed.put(playerChunk, world.getTime());

            for (int s = 0; s < samplesPerPlayer; s++) {
                int x = px + RANDOM.nextInt(sampleRadius * 2 + 1) - sampleRadius;
                int z = pz + RANDOM.nextInt(sampleRadius * 2 + 1) - sampleRadius;
                int y = world.getTopY() - 1;

                BlockPos pos = new BlockPos(x, y, z);
                ChunkPos chunkPos = new ChunkPos(x >> 4, z >> 4);
                
                // Update chunk tracking
                chunkLastProcessed.put(chunkPos, world.getTime());
                
                // Find solid block
                while (pos.getY() > world.getBottomY() && world.isAir(pos)) {
                    pos = pos.down();
                }

                BlockState state = world.getBlockState(pos);
                // If corruption source found, attempt to spread
                if (isCorruptionSource(state)) {
                    // Increase chunk corruption level
                    float currentLevel = chunkCorruptionLevel.getOrDefault(chunkPos, 0.0f);
                    float increment = 0.01f;
                    chunkCorruptionLevel.put(chunkPos, Math.min(1.0f, currentLevel + increment));
                    
                    // Update performance monitor
                    PerformanceMonitor.incrementCorruptedBlocks(chunkPos, 1);
                    
                    if (attemptSpreadFrom(world, pos)) {
                        blocksCorrupted++;
                    }
                }
            }
        }
        
        return blocksCorrupted;
    }

    /**
     * Force an immediate corruption spread pass in this world (used by commands)
     */
    public static void forceSpread(ServerWorld world) {
        if (world == null || world.isClient) return;
        spreadCorruption(world);
        // Reduce delay before next pass using config values
        long minTicks = 20L * CorruptionConfig.corruptionSpreadMinSeconds;
        nextSpreadTick.put(world, world.getTime() + minTicks);
    }
    
    /**
     * Get the corruption level for a specific chunk (0.0 to 1.0)
     */
    public static float getChunkCorruptionLevel(ChunkPos chunkPos) {
        return chunkCorruptionLevel.getOrDefault(chunkPos, 0.0f);
    }
    
    /**
     * Set the corruption level for a specific chunk
     */
    public static void setChunkCorruptionLevel(ChunkPos chunkPos, float level) {
        chunkCorruptionLevel.put(chunkPos, Math.max(0.0f, Math.min(1.0f, level)));
    }

    private static boolean isCorruptionSource(BlockState state) {
        // Any block extending CorruptionBlock is considered a source
        return state.getBlock() instanceof com.corruptionmod.block.CorruptionBlock;
    }

    private static boolean attemptSpreadFrom(ServerWorld world, BlockPos pos) {
        // Cardinal directions + up/down
        BlockPos[] neighbors = new BlockPos[] {
            pos.north(), pos.south(), pos.east(), pos.west(), pos.up(), pos.down()
        };

        // Priority: grass/dirt > stone > wood > sand > other
        for (BlockPos npos : neighbors) {
            // Check if target position is protected by crystal
            if (PurificationManager.isProtectedByCrystal(world, npos)) {
                continue; // Skip this position
            }
            // Check if the target position is within a purified zone
            if (PurificationManager.isInPurifiedZone(world, npos)) {
                continue; // Skip positions protected by purification crystals
            }
            
            BlockState targetState = world.getBlockState(npos);
            if (CorruptionUtil.canBeCorrupted(targetState.getBlock())) {
                float chance = corruptionChanceFor(targetState);
                
                // Apply global corruption multiplier
                chance *= CorruptionConfig.corruptionSpreadMultiplier;
                
                // Slow down water corruption by 80%
                if (targetState.getMaterial() == net.minecraft.block.Material.WATER) chance *= 0.2f;
                
                // Apply warding torch protection (reduces chance)
                float wardingProtection = PurificationManager.getWardingProtection(world, npos);
                chance *= (1.0f - wardingProtection);
                
                if (RANDOM.nextFloat() < chance) {
                    // Choose appropriate corrupted variant
                    BlockState newState = toCorruptedVariant(targetState);
                    world.setBlockState(npos, newState);
                    // Particles and sound effect
                    world.syncWorldEvent(2001, npos, Block.getRawIdFromState(targetState)); // break effect
                    return true; // Block was corrupted
                }
            }
        }
        
        return false; // No block was corrupted
    }

    private static BlockState toCorruptedVariant(BlockState original) {
        String key = original.getBlock().getTranslationKey().toLowerCase();
        if (key.contains("grass")) return ModBlocks.CORRUPTED_GRASS.getDefaultState();
        if (key.contains("dirt")) return ModBlocks.CORRUPTED_DIRT.getDefaultState();
        if (key.contains("stone")) return ModBlocks.CORRUPTED_STONE.getDefaultState();
        if (key.contains("log") || key.contains("wood")) return ModBlocks.ROTTED_WOOD.getDefaultState();
        if (key.contains("sand")) return ModBlocks.CORRUPTED_SAND.getDefaultState();
        if (key.contains("gravel")) return ModBlocks.CORRUPTED_STONE.getDefaultState(); // Gravel becomes corrupted stone
        if (key.contains("water")) return ModBlocks.TAINTED_WATER.getDefaultState();
        if (key.contains("leaves")) return ModBlocks.WITHERED_LEAVES.getDefaultState();
        return ModBlocks.CORRUPTION_BLOCK.getDefaultState();
    }
    

    private static boolean canBeCorrupted(BlockState state) {
        Block block = state.getBlock();
        
        // Cannot corrupt barrier blocks: obsidian, bedrock, purification crystals
        if (block == net.minecraft.block.Blocks.OBSIDIAN || 
            block == net.minecraft.block.Blocks.CRYING_OBSIDIAN ||
            block == net.minecraft.block.Blocks.BEDROCK ||
            block == ModBlocks.PURIFICATION_CRYSTAL) {
            return false;
        }
        
        // Cannot corrupt already corrupted blocks
        if (block instanceof CorruptionBlock) {
            return false;
        }
        
        // Check if block name contains barrier keywords
        String name = block.getTranslationKey().toLowerCase();
        if (name.contains("obsidian") || name.contains("bedrock") || name.contains("purified")) {
            return false;
        }
        
        // Only allow corruption of natural blocks
        return CorruptionUtil.isNaturalBlock(state);
    }

    private static float corruptionChanceFor(BlockState state) {
        String key = state.getBlock().getTranslationKey().toLowerCase();
        if (key.contains("grass") || key.contains("dirt")) return 0.6f;
        if (key.contains("stone")) return 0.35f;
        if (key.contains("log") || key.contains("wood")) return 0.25f;
        if (key.contains("sand")) return 0.2f;
        if (key.contains("gravel")) return 0.3f;
        if (key.contains("leaves")) return 0.3f;
        // Other natural blocks
        return 0.05f;
    }
}
