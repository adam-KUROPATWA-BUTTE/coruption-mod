# Corrupted Entities and Effects Implementation

This document describes the implementation of corrupted entities and corruption effects system for the Corruption Mod.

## Status Effect System

### Corruption Effect
- **Location**: `com.corruptionmod.effect.CorruptionEffect`
- **Type**: Harmful status effect
- **Levels**: 4 (I-IV, represented by amplifiers 0-3)
- **Visual**: Purple/dark color (0x5A1E5C)
- **Particles**: Portal particles that increase with level

#### Level Effects
- **Level I** (Amplifier 0): 0.5 damage every 4 seconds
- **Level II** (Amplifier 1): 1.0 damage every 3 seconds
- **Level III** (Amplifier 2): 1.5 damage every 2 seconds
- **Level IV** (Amplifier 3): 2.0 damage every second

#### Removal
- Milk bucket (vanilla mechanic)
- Purification potion (can be added later)

## Corrupted Entities

### 1. Corrupted Zombie
- **Class**: `CorruptedZombieEntity`
- **Base**: Extends `ZombieEntity`
- **Health**: 25
- **Attack Damage**: 4
- **Speed**: 0.25

#### Special Abilities
- **Corruption Trail**: Leaves corrupted dirt trail while walking (every second)
- **Block Corruption**: Corrupts nearby blocks every 2 seconds
  - Corrupts blocks in 3x3x3 radius
  - 10% chance per block
  - Converts: grass→corrupted_grass, dirt→corrupted_dirt, stone→corrupted_stone, etc.
- **Corruption Effect**: Applies Corruption Level II to targets every 3 seconds
- **Particles**: Purple portal particles

### 2. Corrupted Spider
- **Class**: `CorruptedSpiderEntity`
- **Base**: Extends `SpiderEntity`
- **Health**: 20
- **Attack Damage**: 3
- **Speed**: 0.35

#### Special Abilities
- **Web Placement**: Places corrupting cobwebs while moving (every 3 seconds)
- **Web Corruption**: Webs spread corruption to nearby blocks
  - Checks 5x4x5 radius for webs
  - Corrupts blocks adjacent to webs
  - 5% chance per block per check
- **Enhanced Climbing**: Inherits spider climbing abilities
- **Particles**: Purple portal particles

### 3. Corrupted Creeper
- **Class**: `CorruptedCreeperEntity`
- **Base**: Extends `CreeperEntity`
- **Health**: 22
- **Speed**: 0.27
- **Explosion Radius**: 4.0 (larger than normal)

#### Special Abilities
- **Corruption Explosion**: Explosion corrupts blocks in blast radius
  - Corruption chance based on distance from center
  - Affects blocks in 5x5x5 radius
  - Converts vanilla blocks to corrupted variants
- **Visual Effects**: Purple portal particles, extra smoke when charging
- **Death Behavior**: Corrupts area on death

### 4. Tainted Cow
- **Class**: `TaintedCowEntity`
- **Base**: Extends `PathAwareEntity`
- **Health**: 15
- **Attack Damage**: 2
- **Speed**: 0.22

#### Special Abilities
- **Aggressive Behavior**: Attacks players (unlike normal cows)
- **Tainted Passive Mob**: Example of corrupted passive animals
- **Particles**: Smoke particles

### 5. Hollowed Villager
- **Class**: `HollowedVillagerEntity`
- **Base**: Extends `PathAwareEntity`
- **Health**: 18
- **Attack Damage**: 2.5
- **Speed**: 0.2
- **Corruption Level**: Random 1-4 on spawn

#### Special Abilities
- **Variable Behavior**: 
  - Levels 1-2: Neutral (wanders, doesn't attack)
  - Levels 3-4: Hostile (attacks players)
- **Lost Trading**: No trading abilities
- **Corruption Particles**: Portal particles (frequency based on level)

## AI System

### ApplyCorruptionEffectGoal
- **Location**: `com.corruptionmod.entity.ai.ApplyCorruptionEffectGoal`
- **Purpose**: Applies corruption status effect to nearby targets
- **Parameters**:
  - `corruptionLevel`: 0-3 (Corruption I-IV)
  - `applicationInterval`: Ticks between applications
- **Range**: 4 blocks (16 squared distance)
- **Duration**: 10 seconds per application

## Registration

All entities and effects are registered in:
- **Entities**: `ModEntities.register()` - Uses Fabric's `FabricEntityTypeBuilder`
- **Effects**: `ModEffects.register()` - Registers to `Registry.STATUS_EFFECT`
- **Initialization**: Called from `CorruptionMod.onInitialize()`

## Translation Keys

Added to `en_us.json`:
- `entity.corruptionmod.corrupted_zombie`
- `entity.corruptionmod.corrupted_spider`
- `entity.corruptionmod.corrupted_creeper`
- `entity.corruptionmod.tainted_cow`
- `entity.corruptionmod.hollowed_villager`
- `effect.corruptionmod.corruption`

## Block Corruption Mapping

Corrupted entities convert vanilla blocks to corrupted variants:
- `GRASS_BLOCK` → `CORRUPTED_GRASS`
- `DIRT` → `CORRUPTED_DIRT`
- `STONE` → `CORRUPTED_STONE`
- `SAND` → `CORRUPTED_SAND`
- `OAK_LEAVES/BIRCH_LEAVES/SPRUCE_LEAVES` → `WITHERED_LEAVES`

## Future Enhancements

Potential additions:
1. Custom textures for corrupted entities
2. Custom sounds for corruption effects
3. Purification potion recipe
4. Entity spawning logic in corrupted biomes
5. Loot tables for corrupted entities
6. More tainted animal variants
7. Corruption spread mechanics from entities to environment
8. Player corruption accumulation from environment exposure
