# The Corruption Mod - Package Structure

## Overview
This mod implements a comprehensive corruption system for Minecraft with spreading mechanics, custom blocks, and dimension features.

## Package Structure

### `com.corruptionmod` (Main Package)
- **CorruptionMod.java** - Main mod initializer implementing ModInitializer
  - Initializes all mod components
  - Registers blocks, entities, dimensions, and world generation
  - Sets up ServerTickEvents for corruption spreading
  - MOD_ID: "corruptionmod"

- **ModBlocks.java** - Central block registry
  - Registers all custom blocks with the game
  - Provides static references to block instances

- **ModDimensions.java** - Dimension registration
- **ModEntities.java** - Entity registration  
- **ModWorldGen.java** - World generation features

### `com.corruptionmod.block` (Block System)
Custom blocks implementing the corruption mechanics:

#### Core Corruption Blocks
- **CorruptionBlock.java** - Base corruption source block
  - Spreads corruption to nearby blocks via random ticks
  - Spawns corruption particles
  - Converts vanilla blocks to corrupted variants
  
#### Corrupted Variants
- **CorruptedGrassBlock.java** - Corrupted grass with purple-gray aesthetic
  - Random tick spreading to adjacent grass/dirt
  - Purple particle effects
  - Spreads horizontally to nearby grass blocks

- **CorruptedStoneBlock.java** - Corrupted stone variant
  - Pulsing animation via timed particle effects
  - 40-tick (2 second) pulse cycle
  - Enhanced particles during pulse phase

- **CorruptedDirtBlock.java** - Corrupted dirt variant

- **RottedWoodBlock.java** - Corrupted wood that breaks faster
  - Reduced hardness (1.5f vs normal wood 2.0f)

- **CorruptedSandBlock.java** - Ashen gray corrupted sand
  - Inherits corruption spreading

- **WitheredLeavesBlock.java** - Dark brown corrupted leaves
  - Particle effects (smoke and portal particles)
  - Transparent and non-solid properties
  - Corruption spreading to nearby leaves

- **TaintedWaterBlock.java** - Purple corrupted water
  - Damages living entities on contact (1 damage per tick)
  - No collision for fluid mechanics
  
#### Utility Blocks
- **CorruptionAltarBlock.java** - Ritual/crafting block
- **VoidPortalBlock.java** - Portal frame block
- **PurificationCrystalBlock.java** - Anti-corruption block

### `com.corruptionmod.event` (Event Handlers)
- **WorldCorruptionTicker.java** - Corruption spread system
  - ServerTickEvents.END_WORLD_TICK integration
  - Chunk-based tracking using HashMap<ChunkPos, Float>
  - Tracks corruption level per chunk (0.0 to 1.0)
  - Tracks last processed time per chunk
  - Periodic cleanup of unloaded chunk data
  - Random interval spreading (20-40 seconds)
  - Samples positions around players in loaded chunks
  - Converts vanilla blocks to corrupted variants
  
- **PurificationManager.java** - Purification mechanics

### `com.corruptionmod.entity` (Entities)
- **XynorEntity.java** - Boss entity
- **StrangerEntity.java** - Hostile entity
- **TentacleEntity.java** - Environmental hazard entity

### `com.corruptionmod.command` (Commands)
- **ModCommands.java** - Custom commands for corruption control

### `com.corruptionmod.util` (Utilities)
- **DialogueManager.java** - NPC dialogue system

## Block Registration
All blocks are registered in `ModBlocks.register()` which is called during mod initialization:

```java
CORRUPTION_BLOCK - Source corruption block
CORRUPTED_GRASS - Grass variant
CORRUPTED_DIRT - Dirt variant  
CORRUPTED_STONE - Stone variant with pulsing
ROTTED_WOOD - Wood variant that breaks faster
CORRUPTED_SAND - Sand variant
TAINTED_WATER - Water that damages entities
WITHERED_LEAVES - Leaves with particle effects
CORRUPTION_ALTAR - Ritual block
VOID_PORTAL_FRAME - Portal construction
PURIFICATION_CRYSTAL - Cleansing block
```

## Corruption Spreading Mechanics

### Block-Level Spreading (CorruptionBlock)
- Uses `randomTick()` for individual block spreading
- 15% chance per random tick to attempt spreading
- Prioritizes targets: grass/dirt > stone > wood > sand > water
- Converts to appropriate corrupted variant
- Spawns particles on conversion

### World-Level Spreading (WorldCorruptionTicker)  
- Runs every 20-40 seconds (random interval)
- Samples 8 positions per player within 64 block radius
- Chunk-based tracking maintains corruption intensity
- Automatic cleanup of old chunk data every 5 minutes
- Can be forced via commands

### Corruption Chances by Block Type
- Grass/Dirt: 60%
- Stone: 35%
- Leaves: 30%
- Wood/Logs: 25%
- Sand: 20%
- Water: Reduced by 80% (4% for dirt/grass in water, etc.)
- Other: 5%

## Dependencies
- Fabric Loader >= 0.14.0
- Fabric API
- Minecraft 1.20.1
- GeckoLib >= 4.4 (for boss animations)

## Configuration
- **MOD_ID**: "corruptionmod"
- **Version**: 1.0.0
- **Maven Group**: com.corruptionmod

## Future Enhancements
- Boss implementation using GeckoLib animations
- Dimension features (Le Néant)
- Advanced purification mechanics
- More corrupted block variants
- Corruption biomes
