package com.corruptionmod.data;

import com.corruptionmod.CorruptionMod;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtSizeTracker;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages persistent corruption data storage.
 * Saves corruption levels for chunks to prevent data loss on server restart.
 */
public class CorruptionDataManager extends PersistentState {
    
    private static final String DATA_NAME = "corruption_data";
    private Map<ChunkPos, Float> chunkCorruptionLevels = new HashMap<>();
    
    public CorruptionDataManager() {
        super();
    }
    
    /**
     * Load corruption data from NBT
     */
    public static CorruptionDataManager fromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        CorruptionDataManager manager = new CorruptionDataManager();
        
        if (nbt.contains("ChunkData")) {
            NbtCompound chunkData = nbt.getCompound("ChunkData");
            for (String key : chunkData.getKeys()) {
                try {
                    // Parse chunk position from "x,z" format
                    String[] parts = key.split(",");
                    if (parts.length == 2) {
                        int x = Integer.parseInt(parts[0]);
                        int z = Integer.parseInt(parts[1]);
                        float level = chunkData.getFloat(key);
                        manager.chunkCorruptionLevels.put(new ChunkPos(x, z), level);
                    }
                } catch (NumberFormatException e) {
                    CorruptionMod.LOGGER.warn("Failed to parse chunk position: " + key);
                }
            }
        }
        
        CorruptionMod.LOGGER.info("Loaded corruption data for " + manager.chunkCorruptionLevels.size() + " chunks");
        return manager;
    }
    
    /**
     * Save corruption data to NBT
     */
    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound chunkData = new NbtCompound();
        
        for (Map.Entry<ChunkPos, Float> entry : chunkCorruptionLevels.entrySet()) {
            ChunkPos pos = entry.getKey();
            String key = pos.x + "," + pos.z;
            chunkData.putFloat(key, entry.getValue());
        }
        
        nbt.put("ChunkData", chunkData);
        CorruptionMod.LOGGER.info("Saved corruption data for " + chunkCorruptionLevels.size() + " chunks");
        return nbt;
    }
    
    /**
     * Get or create the corruption data manager for a world
     */
    public static CorruptionDataManager get(MinecraftServer server) {
        return server.getOverworld().getPersistentStateManager().getOrCreate(
            new PersistentState.Type<>(
                CorruptionDataManager::new,
                CorruptionDataManager::fromNbt,
                DataFixTypes.LEVEL
            ),
            DATA_NAME
        );
    }
    
    /**
     * Set corruption level for a chunk
     */
    public void setChunkCorruptionLevel(ChunkPos pos, float level) {
        chunkCorruptionLevels.put(pos, Math.max(0.0f, Math.min(1.0f, level)));
        markDirty();
    }
    
    /**
     * Get corruption level for a chunk
     */
    public float getChunkCorruptionLevel(ChunkPos pos) {
        return chunkCorruptionLevels.getOrDefault(pos, 0.0f);
    }
    
    /**
     * Remove corruption data for a chunk
     */
    public void removeChunk(ChunkPos pos) {
        chunkCorruptionLevels.remove(pos);
        markDirty();
    }
    
    /**
     * Get all chunk corruption levels
     */
    public Map<ChunkPos, Float> getAllChunkLevels() {
        return new HashMap<>(chunkCorruptionLevels);
    }
    
    /**
     * Clear all corruption data (for reset commands)
     */
    public void clearAll() {
        chunkCorruptionLevels.clear();
        markDirty();
    }
    
    /**
     * Create a backup of current data
     */
    public void createBackup(File worldDir) {
        try {
            File backupDir = new File(worldDir, "corruption_backups");
            if (!backupDir.exists()) {
                backupDir.mkdirs();
            }
            
            String timestamp = String.valueOf(System.currentTimeMillis());
            File backupFile = new File(backupDir, DATA_NAME + "_" + timestamp + ".dat");
            
            NbtCompound nbt = new NbtCompound();
            writeNbt(nbt, null); // Registry lookup not needed for backup
            NbtIo.writeCompressed(nbt, backupFile.toPath());
            
            CorruptionMod.LOGGER.info("Created corruption data backup: " + backupFile.getName());
            
            // Clean up old backups (keep only last 5)
            cleanupOldBackups(backupDir);
            
        } catch (IOException e) {
            CorruptionMod.LOGGER.error("Failed to create corruption data backup", e);
        }
    }
    
    /**
     * Restore from a backup file
     */
    public boolean restoreFromBackup(File backupFile) {
        try {
            NbtCompound nbt = NbtIo.readCompressed(backupFile.toPath(), NbtSizeTracker.ofUnlimitedBytes());
            chunkCorruptionLevels.clear();
            
            CorruptionDataManager restored = fromNbt(nbt, null); // Registry lookup not needed for restore
            chunkCorruptionLevels.putAll(restored.chunkCorruptionLevels);
            markDirty();
            
            CorruptionMod.LOGGER.info("Restored corruption data from backup: " + backupFile.getName());
            return true;
            
        } catch (IOException e) {
            CorruptionMod.LOGGER.error("Failed to restore from backup", e);
            return false;
        }
    }
    
    /**
     * Clean up old backup files, keeping only the most recent ones
     */
    private void cleanupOldBackups(File backupDir) {
        File[] backups = backupDir.listFiles((dir, name) -> name.endsWith(".dat"));
        if (backups == null || backups.length <= 5) {
            return; // Keep all if 5 or fewer
        }
        
        // Sort by last modified time
        java.util.Arrays.sort(backups, (a, b) -> Long.compare(b.lastModified(), a.lastModified()));
        
        // Delete oldest backups beyond the 5 most recent
        for (int i = 5; i < backups.length; i++) {
            if (backups[i].delete()) {
                CorruptionMod.LOGGER.info("Deleted old backup: " + backups[i].getName());
            }
        }
    }
    
    /**
     * Validate data integrity
     */
    public boolean validate() {
        try {
            for (Map.Entry<ChunkPos, Float> entry : chunkCorruptionLevels.entrySet()) {
                float level = entry.getValue();
                if (level < 0.0f || level > 1.0f) {
                    CorruptionMod.LOGGER.warn("Invalid corruption level detected: " + level + " at " + entry.getKey());
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            CorruptionMod.LOGGER.error("Error validating corruption data", e);
            return false;
        }
    }
    
    /**
     * Get statistics about stored data
     */
    public DataStats getStats() {
        int totalChunks = chunkCorruptionLevels.size();
        int lowCorruption = 0;
        int mediumCorruption = 0;
        int highCorruption = 0;
        float totalLevel = 0.0f;
        
        for (float level : chunkCorruptionLevels.values()) {
            totalLevel += level;
            if (level < 0.33f) {
                lowCorruption++;
            } else if (level < 0.66f) {
                mediumCorruption++;
            } else {
                highCorruption++;
            }
        }
        
        float averageLevel = totalChunks > 0 ? totalLevel / totalChunks : 0.0f;
        
        return new DataStats(totalChunks, lowCorruption, mediumCorruption, highCorruption, averageLevel);
    }
    
    /**
     * Statistics about corruption data
     */
    public static class DataStats {
        public final int totalChunks;
        public final int lowCorruption;
        public final int mediumCorruption;
        public final int highCorruption;
        public final float averageLevel;
        
        public DataStats(int totalChunks, int lowCorruption, int mediumCorruption, int highCorruption, float averageLevel) {
            this.totalChunks = totalChunks;
            this.lowCorruption = lowCorruption;
            this.mediumCorruption = mediumCorruption;
            this.highCorruption = highCorruption;
            this.averageLevel = averageLevel;
        }
    }
}
