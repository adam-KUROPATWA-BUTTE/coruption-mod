package com.corruptionmod.config;

/**
 * Configuration for the Corruption Mod.
 * Contains all configurable options for performance, gameplay, visuals, and audio.
 */
public class CorruptionConfig {
    
    // ===== Performance Settings =====
    
    /** Minimum seconds between corruption spread ticks (default: 20) */
    public static int corruptionSpreadMinSeconds = 20;
    
    /** Maximum seconds between corruption spread ticks (default: 40) */
    public static int corruptionSpreadMaxSeconds = 40;
    
    /** Number of positions sampled per player per spread tick (default: 8) */
    public static int samplesPerPlayer = 8;
    
    /** Radius around each player to sample for corruption (default: 64) */
    public static int sampleRadius = 64;
    
    /** Maximum corruption spread radius per tick (default: 16) */
    public static int maxSpreadRadiusPerTick = 16;
    
    /** Enable batch block updates to reduce lag (default: true) */
    public static boolean enableBatchUpdates = true;
    
    /** Enable dirty chunk tracking (default: true) */
    public static boolean enableDirtyChunkTracking = true;
    
    /** Cleanup interval for chunk data in minutes (default: 5) */
    public static int chunkDataCleanupMinutes = 5;
    
    /** Chunk data expiry time in minutes (default: 10) */
    public static int chunkDataExpiryMinutes = 10;
    
    // ===== Gameplay Settings =====
    
    /** Corruption spread chance multiplier (default: 1.0) */
    public static double corruptionSpreadMultiplier = 1.0;
    
    /** Enable corruption in specific dimensions (default: true for overworld) */
    public static boolean enableOverworldCorruption = true;
    
    /** Boss difficulty multiplier (default: 1.0) */
    public static double bossDifficultyMultiplier = 1.0;
    
    /** Entity spawn rate multiplier (default: 1.0) */
    public static double entitySpawnRateMultiplier = 1.0;
    
    /** Enable player corruption effect (default: true) */
    public static boolean enablePlayerCorruption = true;
    
    /** Purification crystal range in blocks (default: 16) */
    public static int purificationCrystalRange = 16;
    
    // ===== Visual Settings =====
    
    /** Particle density multiplier (default: 1.0, set lower for potato PCs) */
    public static double particleDensityMultiplier = 1.0;
    
    /** Enable corruption fog (default: true) */
    public static boolean enableCorruptionFog = true;
    
    /** Corruption fog density (default: 0.5) */
    public static double corruptionFogDensity = 0.5;
    
    /** Enable void realm atmosphere effects (default: true) */
    public static boolean enableVoidRealmAtmosphere = true;
    
    /** Enable block animations (default: true) */
    public static boolean enableBlockAnimations = true;
    
    /** Enable custom lighting (default: true) */
    public static boolean enableCustomLighting = true;
    
    // ===== Audio Settings =====
    
    /** Master volume for mod sounds (default: 1.0) */
    public static double masterVolume = 1.0;
    
    /** Ambient sound volume (default: 0.5) */
    public static double ambientSoundVolume = 0.5;
    
    /** Music volume (default: 0.7) */
    public static double musicVolume = 0.7;
    
    /** Entity sound volume (default: 0.8) */
    public static double entitySoundVolume = 0.8;
    
    /** Block sound volume (default: 0.6) */
    public static double blockSoundVolume = 0.6;
    
    /** Enable ambient sounds (default: true) */
    public static boolean enableAmbientSounds = true;
    
    /** Enable custom music (default: true) */
    public static boolean enableCustomMusic = true;
    
    // ===== Multiplayer Settings =====
    
    /** Corruption sync frequency in ticks (default: 20, once per second) */
    public static int corruptionSyncFrequency = 20;
    
    /** Enable client-side prediction (default: true) */
    public static boolean enableClientPrediction = true;
    
    /** Maximum packet size for corruption data (default: 32KB) */
    public static int maxPacketSize = 32768;
    
    /** Enable delta compression for updates (default: true) */
    public static boolean enableDeltaCompression = true;
    
    // ===== Debug Settings =====
    
    /** Enable debug mode (default: false) */
    public static boolean debugMode = false;
    
    /** Enable debug overlay on F3 screen (default: false) */
    public static boolean enableDebugOverlay = false;
    
    /** Enable performance profiling logs (default: false) */
    public static boolean enableProfilingLogs = false;
    
    /** Enable memory monitoring (default: false) */
    public static boolean enableMemoryMonitoring = false;
    
    /**
     * Reload configuration from file (placeholder for future file-based config)
     */
    public static void reload() {
        // TODO: Implement file-based configuration loading
        // For now, values are hardcoded with defaults
    }
    
    /**
     * Save current configuration to file (placeholder for future file-based config)
     */
    public static void save() {
        // TODO: Implement file-based configuration saving
    }
    
    /**
     * Reset all configuration values to defaults
     */
    public static void resetToDefaults() {
        corruptionSpreadMinSeconds = 20;
        corruptionSpreadMaxSeconds = 40;
        samplesPerPlayer = 8;
        sampleRadius = 64;
        maxSpreadRadiusPerTick = 16;
        enableBatchUpdates = true;
        enableDirtyChunkTracking = true;
        chunkDataCleanupMinutes = 5;
        chunkDataExpiryMinutes = 10;
        
        corruptionSpreadMultiplier = 1.0;
        enableOverworldCorruption = true;
        bossDifficultyMultiplier = 1.0;
        entitySpawnRateMultiplier = 1.0;
        enablePlayerCorruption = true;
        purificationCrystalRange = 16;
        
        particleDensityMultiplier = 1.0;
        enableCorruptionFog = true;
        corruptionFogDensity = 0.5;
        enableVoidRealmAtmosphere = true;
        enableBlockAnimations = true;
        enableCustomLighting = true;
        
        masterVolume = 1.0;
        ambientSoundVolume = 0.5;
        musicVolume = 0.7;
        entitySoundVolume = 0.8;
        blockSoundVolume = 0.6;
        enableAmbientSounds = true;
        enableCustomMusic = true;
        
        corruptionSyncFrequency = 20;
        enableClientPrediction = true;
        maxPacketSize = 32768;
        enableDeltaCompression = true;
        
        debugMode = false;
        enableDebugOverlay = false;
        enableProfilingLogs = false;
        enableMemoryMonitoring = false;
    }
}
