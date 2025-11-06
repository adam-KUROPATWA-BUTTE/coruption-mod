# Corruption Spread System Documentation

## Overview
The Corruption Spread Mechanic is the core gameplay feature that makes The Corruption Mod engaging and challenging. It implements a sophisticated, chunk-based system for spreading corruption throughout the Minecraft world.

## Core Components

### 1. WorldCorruptionTicker (`event/WorldCorruptionTicker.java`)
The main system that manages world-level corruption spreading.

#### Key Features:
- **Random Interval Spreading**: Spreads corruption every 20-40 seconds (randomized)
- **Chunk-Based Processing**: Uses HashMap to efficiently track corruption per chunk
- **Player-Based Sampling**: Samples 8 positions within 64 blocks of each player
- **Smart Caching**: Tracks chunk corruption levels and last processed times
- **Automatic Cleanup**: Removes stale chunk data every 5 minutes

#### Configuration Constants:
```java
MIN_TICKS = 400 ticks (20 seconds)         // Minimum spread interval
MAX_TICKS = 800 ticks (40 seconds)         // Maximum spread interval
SAMPLES_PER_PLAYER = 8                     // Positions checked per player
SAMPLE_RADIUS = 64 blocks                  // Range around players to check
CLEANUP_INTERVAL = 6000 ticks (5 minutes)  // How often to cleanup old data
CHUNK_DATA_EXPIRY = 12000 ticks (10 min)   // When to remove chunk data
CHUNK_CORRUPTION_INCREMENT = 0.01          // Corruption level increase per spread
```

### 2. CorruptionBlock (`block/CorruptionBlock.java`)
Base class for all corruption source blocks. Implements random tick spreading.

#### Key Features:
- **Random Tick Spreading**: 15% chance per random tick to attempt spread
- **Priority-Based Target Selection**: Chooses highest-priority neighbor
- **Particle Effects**: Spawns portal particles on corruption spread
- **Sound Effects**: Plays block break sound on conversion

### 3. PurificationManager (`event/PurificationManager.java`)
Manages purification crystals and protected zones.

#### Key Features:
- **Zone Protection**: Prevents corruption within 32-block radius of crystals
- **Gradual Purification**: Reverts one corrupted block every 10 seconds per crystal
- **Dynamic Crystal Management**: Tracks crystals as they're placed/removed

## Spread Mechanics

### Block Type Priority
Corruption prioritizes natural blocks in this order:
1. **Grass/Dirt** → Corrupted Grass/Dirt (60% chance)
2. **Stone** → Corrupted Stone (35% chance)
3. **Leaves** → Withered Leaves (30% chance)
4. **Gravel** → Corrupted Stone (30% chance)
5. **Wood/Logs** → Rotted Wood (25% chance)
6. **Sand** → Corrupted Sand (20% chance)
7. **Water** → Tainted Water (~4-12% chance - 80% slowdown applied)
8. **Other Natural Blocks** → Corruption Block (5% chance)

### Water Interaction
- Water **slows** corruption spread by 80%
- Base chances are multiplied by 0.2 (20% of normal)
- Prevents rapid spread through oceans and rivers

### Barrier Blocks
Corruption **cannot** spread through:
- ✓ Obsidian (including Crying Obsidian)
- ✓ Bedrock
- ✓ Purification Crystals
- ✓ Any block with "purified" in its name
- ✓ Already-corrupted blocks (prevents re-corruption)

### Purification Zones
- Purification Crystals create a **32-block radius** protected zone
- Corruption sources within this zone still exist but cannot spread
- Crystals slowly revert corrupted blocks to clean variants

## Performance Optimizations

### 1. Chunk-Based Processing
- Uses `HashMap<ChunkPos, Float>` to track corruption per chunk
- Only processes chunks with active players nearby
- Limits sampling to 8 positions per player per pass

### 2. Event-Driven Architecture
- Registered with `ServerTickEvents.END_WORLD_TICK`
- Only executes at randomized intervals (20-40s)
- Avoids continuous processing every tick

### 3. Smart Caching
- `chunkCorruptionLevel`: Tracks intensity (0.0-1.0) per chunk
- `chunkLastProcessed`: Tracks last update time per chunk
- `nextSpreadTick`: Tracks next spread time per world

### 4. Automatic Cleanup
- Removes chunk data not accessed in 10 minutes
- Runs cleanup every 5 minutes
- Prevents memory leaks from unloaded chunks

### 5. Throttling Mechanisms
- Random intervals prevent predictable server load
- Limited samples per player (8) per pass
- Only one neighbor infected per source per pass
- Percentage-based spread chances prevent exponential growth

## Block Variants

### Corrupted Block Types
1. **CorruptedGrassBlock**: Purple-gray grass with horizontal spread
2. **CorruptedDirtBlock**: Basic corrupted dirt
3. **CorruptedStoneBlock**: Pulsing stone with animated particles
4. **RottedWoodBlock**: Weakened wood (1.5f hardness)
5. **CorruptedSandBlock**: Ashen gray falling sand
6. **WitheredLeavesBlock**: Dark brown leaves with ash particles
7. **TaintedWaterBlock**: Damaging purple water
8. **CorruptionBlock**: Generic corruption source

### Special Behaviors
- **CorruptedGrassBlock**: Can spread horizontally to adjacent grass (3x3 area)
- **CorruptedStoneBlock**: Pulses every 2 seconds with particle effects
- **TaintedWaterBlock**: Deals 1 damage per tick to living entities
- **WitheredLeavesBlock**: Spawns falling ash particles

## Visual & Audio Effects

### Particle Effects
- **Portal Particles**: Main corruption indicator (purple swirls)
- **Smoke Particles**: Ambient corruption effect
- **Ash Particles**: Withered leaves decay effect
- **Burst Effects**: 6 particles spawn on successful conversion

### Sound Effects
- Block break sound (event 2001) plays on conversion
- Uses original block's break sound for authenticity

## API Methods

### WorldCorruptionTicker
```java
// Force immediate corruption spread pass
WorldCorruptionTicker.forceSpread(ServerWorld world)

// Get chunk corruption level (0.0 to 1.0)
float level = WorldCorruptionTicker.getChunkCorruptionLevel(ChunkPos pos)

// Set chunk corruption level
WorldCorruptionTicker.setChunkCorruptionLevel(ChunkPos pos, float level)
```

### PurificationManager
```java
// Check if position is protected by a crystal
boolean protected = PurificationManager.isInPurifiedZone(ServerWorld world, BlockPos pos)

// Manually add/remove crystals (usually called by crystal block)
PurificationManager.addCrystal(ServerWorld world, BlockPos pos)
PurificationManager.removeCrystal(ServerWorld world, BlockPos pos)
```

## Integration Points

### 1. Main Mod Initialization
```java
// In CorruptionMod.onInitialize()
ServerTickEvents.END_WORLD_TICK.register(world -> {
    WorldCorruptionTicker.tick(world);
});

PurificationManager.register();
```

### 2. Block Registration
```java
// In ModBlocks.register()
CORRUPTION_BLOCK = Registry.register(...)
CORRUPTED_GRASS = Registry.register(...)
// ... all other variants
```

### 3. Random Ticks
All corrupted block variants that extend `CorruptionBlock` automatically get random tick spreading behavior.

## Future Enhancements

Potential improvements for consideration:
1. **Biome-Specific Spread Rates**: Different speeds in different biomes
2. **Weather Effects**: Faster spread during storms
3. **Dimension Variations**: Different rules in Nether/End
4. **Corruption Levels**: Multiple stages of corruption
5. **Player Actions**: Certain actions could accelerate/decelerate spread
6. **Network Sync**: Particle effects for better multiplayer experience

## Testing Recommendations

1. **Performance Testing**:
   - Test with 10+ players in different areas
   - Monitor tick time impact
   - Check memory usage over extended play

2. **Gameplay Testing**:
   - Verify spread rates feel appropriate
   - Test purification crystal effectiveness
   - Ensure barrier blocks work correctly
   - Test water slowdown effect

3. **Edge Cases**:
   - World borders
   - Dimension transitions
   - Chunk loading/unloading
   - Server restarts (persistence)

## Troubleshooting

### Corruption spreading too fast/slow
Adjust `MIN_TICKS`, `MAX_TICKS`, or corruption chance percentages in `corruptionChanceFor()`

### Performance issues
- Reduce `SAMPLES_PER_PLAYER` (currently 8)
- Reduce `SAMPLE_RADIUS` (currently 64)
- Increase `MIN_TICKS`/`MAX_TICKS` intervals

### Purification not working
- Check crystal placement (must be on solid block)
- Verify `PURIFICATION_RADIUS` (32 blocks)
- Ensure crystals are registered in PurificationManager

## Conclusion

This corruption spread system provides a balanced, performant, and engaging gameplay mechanic. It uses modern Minecraft modding practices with Fabric API and implements sophisticated optimization techniques to ensure smooth gameplay even with extensive corruption.
