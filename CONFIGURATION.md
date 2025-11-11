# Corruption Mod Configuration Guide

This guide explains all configuration options available in the Corruption Mod and how to tune them for your gameplay experience.

## Configuration Location

Currently, configuration is managed through the `CorruptionConfig` class located at:
```
src/main/java/com/corruptionmod/config/CorruptionConfig.java
```

**Note**: A user-friendly config file system is planned for future releases.

## Performance Settings

### Corruption Spread

**`corruptionSpreadMinSeconds`** (Default: 20)
- Minimum time in seconds between corruption spread cycles
- Lower values = faster spreading, more load
- Recommended range: 10-30

**`corruptionSpreadMaxSeconds`** (Default: 40)
- Maximum time in seconds between corruption spread cycles
- The actual interval is randomly chosen between min and max
- Recommended range: 30-60

**`samplesPerPlayer`** (Default: 8)
- Number of blocks checked per player during each spread cycle
- Higher values = more thorough spreading, more load
- Recommended range: 4-16

**`sampleRadius`** (Default: 64)
- Radius in blocks around each player to check for corruption
- Larger radius = wider spread area, more load
- Recommended range: 32-128

**`maxSpreadRadiusPerTick`** (Default: 16)
- Maximum distance corruption can spread in a single tick
- Limits single-tick performance impact
- Recommended range: 8-32

### Optimization Features

**`enableBatchUpdates`** (Default: true)
- Batches multiple block updates to reduce lag
- Recommended: Keep enabled unless debugging

**`enableDirtyChunkTracking`** (Default: true)
- Tracks which chunks need corruption processing
- Significantly reduces unnecessary checks
- Recommended: Keep enabled

**`chunkDataCleanupMinutes`** (Default: 5)
- How often to clean up old chunk data
- Lower values = more frequent cleanup, slight overhead
- Recommended range: 3-10

**`chunkDataExpiryMinutes`** (Default: 10)
- How long to keep chunk data before removing it
- Should be larger than cleanup interval
- Recommended range: 5-20

## Gameplay Settings

### Corruption Mechanics

**`corruptionSpreadMultiplier`** (Default: 1.0)
- Global multiplier for all corruption spread chances
- 0.5 = half speed, 2.0 = double speed
- Recommended range: 0.5-2.0

**`enableOverworldCorruption`** (Default: true)
- Whether corruption spreads in the Overworld
- Disable for dimension-only corruption gameplay

**`enablePlayerCorruption`** (Default: true)
- Whether players can be corrupted
- Disable for a more forgiving experience

### Difficulty

**`bossDifficultyMultiplier`** (Default: 1.0)
- Scales boss health and damage
- 0.5 = easier, 2.0 = harder
- Recommended range: 0.5-3.0

**`entitySpawnRateMultiplier`** (Default: 1.0)
- Scales corrupted entity spawn frequency
- Recommended range: 0.5-2.0

### Purification

**`purificationCrystalRange`** (Default: 16)
- Range in blocks that purification crystals protect
- Larger range = easier to protect areas
- Recommended range: 8-32

## Visual Settings

### Particles

**`particleDensityMultiplier`** (Default: 1.0)
- Global particle density multiplier
- 0.5 = half particles, 0 = no particles
- **For low-end PCs**: Set to 0.3-0.5
- Recommended range: 0.0-1.5

**`enableBlockAnimations`** (Default: true)
- Enables particle effects and block animations
- Disable for best performance on potato PCs

### Atmosphere

**`enableCorruptionFog`** (Default: true)
- Enables fog effect in corrupted areas
- Moderate performance impact

**`corruptionFogDensity`** (Default: 0.5)
- How thick the corruption fog appears
- Only applies if fog is enabled
- Recommended range: 0.3-1.0

**`enableVoidRealmAtmosphere`** (Default: true)
- Special atmospheric effects in the Void Realm
- Minimal performance impact

**`enableCustomLighting`** (Default: true)
- Custom light sources for corruption blocks
- Minimal performance impact

## Audio Settings

### Volume Controls

**`masterVolume`** (Default: 1.0)
- Master volume for all mod sounds
- 0.0 = muted, 1.0 = full volume

**`ambientSoundVolume`** (Default: 0.5)
- Volume for ambient sounds (whispers, heartbeat, etc.)

**`musicVolume`** (Default: 0.7)
- Volume for custom music tracks

**`entitySoundVolume`** (Default: 0.8)
- Volume for entity sounds

**`blockSoundVolume`** (Default: 0.6)
- Volume for block sounds

### Sound Features

**`enableAmbientSounds`** (Default: true)
- Enables eerie ambient sounds in corrupted areas
- Disable if sounds are too unsettling

**`enableCustomMusic`** (Default: true)
- Enables custom music tracks for boss and void realm
- No performance impact

## Multiplayer Settings

**`corruptionSyncFrequency`** (Default: 20)
- How often (in ticks) to sync corruption between server and clients
- 20 ticks = once per second
- Lower = more responsive, more network traffic
- Recommended range: 10-40

**`enableClientPrediction`** (Default: true)
- Clients predict corruption spread locally
- Reduces perceived lag
- Recommended: Keep enabled

**`maxPacketSize`** (Default: 32768)
- Maximum size in bytes for corruption data packets
- Adjust if experiencing network issues
- Recommended range: 16384-65536

**`enableDeltaCompression`** (Default: true)
- Only sends changed corruption data
- Significantly reduces network traffic
- Recommended: Keep enabled

## Debug Settings

**`debugMode`** (Default: false)
- Enables debug logging
- Use for troubleshooting issues

**`enableDebugOverlay`** (Default: false)
- Shows corruption data on F3 debug screen
- Useful for development and testing

**`enableProfilingLogs`** (Default: false)
- Logs detailed performance profiling data
- Creates large log files
- Use only when diagnosing performance issues

**`enableMemoryMonitoring`** (Default: false)
- Tracks detailed memory usage
- Minimal overhead
- Enable if experiencing memory issues

## Recommended Presets

### Potato PC
```java
particleDensityMultiplier = 0.3
enableBlockAnimations = true
enableCorruptionFog = false
corruptionSpreadMinSeconds = 30
corruptionSpreadMaxSeconds = 60
samplesPerPlayer = 4
sampleRadius = 48
```

### Balanced (Default)
All settings at their default values provide a balanced experience.

### Performance
```java
corruptionSpreadMinSeconds = 40
corruptionSpreadMaxSeconds = 80
samplesPerPlayer = 6
sampleRadius = 48
particleDensityMultiplier = 0.7
enableCorruptionFog = false
```

### Maximum Immersion
```java
particleDensityMultiplier = 1.5
enableBlockAnimations = true
enableCorruptionFog = true
corruptionFogDensity = 0.8
enableAmbientSounds = true
enableCustomMusic = true
ambientSoundVolume = 0.7
```

### Hardcore Challenge
```java
corruptionSpreadMultiplier = 2.0
bossDifficultyMultiplier = 1.5
entitySpawnRateMultiplier = 1.5
corruptionSpreadMinSeconds = 10
corruptionSpreadMaxSeconds = 20
purificationCrystalRange = 12
```

### Peaceful Builder
```java
corruptionSpreadMultiplier = 0.3
entitySpawnRateMultiplier = 0.5
bossDifficultyMultiplier = 0.7
corruptionSpreadMinSeconds = 60
corruptionSpreadMaxSeconds = 120
purificationCrystalRange = 24
```

## Server Configuration

### For Dedicated Servers

**Recommended Settings**:
```java
// Performance optimization for servers
enableBatchUpdates = true
enableDirtyChunkTracking = true
samplesPerPlayer = 6
sampleRadius = 48

// Network optimization
corruptionSyncFrequency = 20
enableDeltaCompression = true
maxPacketSize = 32768

// Disable client-only features
enableDebugOverlay = false
```

### Large Player Count (10+ players)
```java
samplesPerPlayer = 4
sampleRadius = 32
corruptionSpreadMinSeconds = 30
corruptionSpreadMaxSeconds = 50
```

## Performance Tips

1. **Particle Optimization**: Reduce `particleDensityMultiplier` for immediate FPS improvement
2. **Spread Intervals**: Increase spread intervals for smoother gameplay on loaded servers
3. **Sample Reduction**: Lower `samplesPerPlayer` in crowded areas
4. **Fog Disable**: Turn off fog for best performance
5. **Batch Updates**: Always keep enabled for best server performance
6. **Clean Shutdown**: Ensure proper world saving to maintain corruption data integrity

## Troubleshooting

### High Memory Usage
- Enable `enableMemoryMonitoring`
- Reduce `chunkDataExpiryMinutes`
- Increase `chunkDataCleanupMinutes` frequency

### Lag Spikes
- Increase spread intervals
- Reduce `samplesPerPlayer`
- Enable `enableBatchUpdates`
- Reduce particle density

### Network Issues (Multiplayer)
- Increase `corruptionSyncFrequency` (e.g., to 40)
- Ensure `enableDeltaCompression` is true
- Adjust `maxPacketSize` if needed

### Corruption Too Slow/Fast
- Adjust `corruptionSpreadMultiplier`
- Modify min/max spread seconds
- Change `samplesPerPlayer`

## Future Configuration

A configuration file system (`.toml` or similar) with in-game GUI is planned for future updates, which will make these settings more accessible without code modification.

---

**Need Help?** Report issues on the [GitHub repository](https://github.com/adam-KUROPATWA-BUTTE/coruption-mod/issues)
