package com.corruptionmod.util;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Tracks performance statistics for the Corruption Mod.
 * Provides data for the /corruption stats command and debug overlay.
 */
public class PerformanceMonitor {
    
    // Corruption statistics
    private static final Map<ChunkPos, Integer> corruptedBlocksPerChunk = new HashMap<>();
    private static final AtomicInteger totalCorruptedBlocks = new AtomicInteger(0);
    private static final AtomicInteger activeCorruptedChunks = new AtomicInteger(0);
    private static final AtomicInteger activeCorruptedEntities = new AtomicInteger(0);
    
    // Spread statistics
    private static final AtomicLong lastSpreadTime = new AtomicLong(0);
    private static final AtomicInteger blocksCorruptedLastTick = new AtomicInteger(0);
    private static final AtomicLong totalSpreadEvents = new AtomicLong(0);
    
    // Performance statistics
    private static final AtomicLong totalTickTime = new AtomicLong(0);
    private static final AtomicLong tickCount = new AtomicLong(0);
    private static final AtomicLong peakTickTime = new AtomicLong(0);
    
    // Memory statistics
    private static long lastMemoryCheck = 0;
    private static long usedMemory = 0;
    private static long maxMemory = 0;
    
    /**
     * Record a corruption spread event
     */
    public static void recordSpreadEvent(int blocksCorrupted) {
        totalSpreadEvents.incrementAndGet();
        blocksCorruptedLastTick.set(blocksCorrupted);
        lastSpreadTime.set(System.currentTimeMillis());
    }
    
    /**
     * Record tick execution time
     */
    public static void recordTickTime(long nanoTime) {
        totalTickTime.addAndGet(nanoTime);
        tickCount.incrementAndGet();
        
        if (nanoTime > peakTickTime.get()) {
            peakTickTime.set(nanoTime);
        }
    }
    
    /**
     * Update corrupted block count for a chunk
     */
    public static void setChunkCorruptedBlocks(ChunkPos pos, int count) {
        if (count <= 0) {
            corruptedBlocksPerChunk.remove(pos);
        } else {
            corruptedBlocksPerChunk.put(pos, count);
        }
        recalculateTotals();
    }
    
    /**
     * Increment corrupted block count
     */
    public static void incrementCorruptedBlocks(ChunkPos pos, int amount) {
        int current = corruptedBlocksPerChunk.getOrDefault(pos, 0);
        setChunkCorruptedBlocks(pos, current + amount);
    }
    
    /**
     * Decrement corrupted block count
     */
    public static void decrementCorruptedBlocks(ChunkPos pos, int amount) {
        int current = corruptedBlocksPerChunk.getOrDefault(pos, 0);
        setChunkCorruptedBlocks(pos, Math.max(0, current - amount));
    }
    
    /**
     * Set active corrupted entity count
     */
    public static void setActiveCorruptedEntities(int count) {
        activeCorruptedEntities.set(count);
    }
    
    /**
     * Update memory statistics
     */
    public static void updateMemoryStats() {
        Runtime runtime = Runtime.getRuntime();
        maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        usedMemory = totalMemory - freeMemory;
        lastMemoryCheck = System.currentTimeMillis();
    }
    
    /**
     * Recalculate total statistics
     */
    private static void recalculateTotals() {
        int total = 0;
        int chunks = 0;
        
        for (int count : corruptedBlocksPerChunk.values()) {
            total += count;
            if (count > 0) {
                chunks++;
            }
        }
        
        totalCorruptedBlocks.set(total);
        activeCorruptedChunks.set(chunks);
    }
    
    /**
     * Get total number of corrupted blocks
     */
    public static int getTotalCorruptedBlocks() {
        return totalCorruptedBlocks.get();
    }
    
    /**
     * Get number of active corrupted chunks
     */
    public static int getActiveCorruptedChunks() {
        return activeCorruptedChunks.get();
    }
    
    /**
     * Get number of active corrupted entities
     */
    public static int getActiveCorruptedEntities() {
        return activeCorruptedEntities.get();
    }
    
    /**
     * Get corruption spread rate (blocks per second)
     */
    public static double getCorruptionSpreadRate() {
        if (totalSpreadEvents.get() == 0) return 0.0;
        
        long now = System.currentTimeMillis();
        long elapsed = now - lastSpreadTime.get();
        
        if (elapsed > 60000) { // Reset if more than 1 minute
            return 0.0;
        }
        
        return blocksCorruptedLastTick.get();
    }
    
    /**
     * Get average tick time in milliseconds
     */
    public static double getAverageTickTimeMs() {
        long count = tickCount.get();
        if (count == 0) return 0.0;
        
        return (totalTickTime.get() / count) / 1_000_000.0;
    }
    
    /**
     * Get peak tick time in milliseconds
     */
    public static double getPeakTickTimeMs() {
        return peakTickTime.get() / 1_000_000.0;
    }
    
    /**
     * Get used memory in MB
     */
    public static double getUsedMemoryMB() {
        if (System.currentTimeMillis() - lastMemoryCheck > 5000) {
            updateMemoryStats();
        }
        return usedMemory / (1024.0 * 1024.0);
    }
    
    /**
     * Get max memory in MB
     */
    public static double getMaxMemoryMB() {
        if (System.currentTimeMillis() - lastMemoryCheck > 5000) {
            updateMemoryStats();
        }
        return maxMemory / (1024.0 * 1024.0);
    }
    
    /**
     * Get memory usage percentage
     */
    public static double getMemoryUsagePercent() {
        if (maxMemory == 0) {
            updateMemoryStats();
        }
        if (maxMemory == 0) return 0.0;
        return (usedMemory * 100.0) / maxMemory;
    }
    
    /**
     * Reset all statistics
     */
    public static void reset() {
        corruptedBlocksPerChunk.clear();
        totalCorruptedBlocks.set(0);
        activeCorruptedChunks.set(0);
        activeCorruptedEntities.set(0);
        blocksCorruptedLastTick.set(0);
        totalSpreadEvents.set(0);
        totalTickTime.set(0);
        tickCount.set(0);
        peakTickTime.set(0);
    }
    
    /**
     * Clear chunk data for unloaded chunks
     */
    public static void clearChunkData(ChunkPos pos) {
        corruptedBlocksPerChunk.remove(pos);
        recalculateTotals();
    }
}
