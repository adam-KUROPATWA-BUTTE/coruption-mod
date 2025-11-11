# The Harbinger of Entropy - Boss Implementation Guide

## Overview
The Harbinger is the endgame boss of the Corruption Mod, featuring a three-phase combat system with varied attacks and mechanics.

## Entity Details
- **Health**: 500 HP (250 hearts)
- **Base Damage**: 15 (7.5 hearts)
- **Speed**: Moderate with teleportation
- **Size**: Large imposing model (1.5 x 3.5 blocks)
- **Type**: Hostile Boss Entity

## Combat Phases

### Phase 1: Awakening (100%-66% HP)
**Abilities:**
- **Melee Attack**: Basic close-range attack
- **Void Bolt**: Ranged projectile dealing 8 damage (60 tick cooldown)
- **Tentacle Slam**: AOE attack in 5-block radius dealing 10 damage (80 tick cooldown)
- **Minion Summoning**: Spawns 2 Corrupted Zombies every 30 seconds (600 ticks)

**Speed Modifier**: Base (0.3)
**Knockback Resistance**: 50%

### Phase 2: Enraged (66%-33% HP)
**New Abilities:**
- **Teleport Strike**: Teleports behind player and attacks (100 tick cooldown)
- **Void Pulse**: AOE damage wave dealing 12 damage in 10-block radius (300 tick cooldown)
- **Enhanced Summoning**: Spawns 3 Corrupted Zombies + 1 Corrupted Spider every 25 seconds (500 ticks)
- **Corruption Effect**: All attacks now apply Corruption status effect

**Speed Modifier**: 20% increase (0.36)
**Boss Bar Color**: Changes to RED
**Animation**: Phase transition with dramatic particle effects

### Phase 3: Reality Shattered (33%-0% HP)
**New Abilities:**
- **Reality Tear**: Creates damaging void rifts at random positions near players (120 tick cooldown)
- **Void Cascade**: Massive AOE dealing 20 damage in 15-block radius with knockback (400 tick cooldown)
- **Continuous Summoning**: Spawns 2 Corrupted Zombies + 1 Corrupted Spider every 20 seconds (400 ticks)

**Speed Modifier**: 30% increase (0.39)
**Knockback Resistance**: 80%
**Boss Bar Color**: Changes to PINK

## Loot Drops

### Guaranteed Drops
- **Harbinger's Core**: 1 piece - Main crafting material
- **Entropy Essence**: 5-10 pieces - Crafting material
- **Corruption Crystal**: 3-5 pieces - Crafting material

### Rare Drops (33% chance each)
- **Entropy Blade**: Sword with 12 attack damage, applies Corruption on hit
- **Harbinger's Helmet**: Provides Night Vision
- **Harbinger's Chestplate**: Provides Health Boost
- **Harbinger's Leggings**: Provides Speed
- **Harbinger's Boots**: Provides Jump Boost

### Special Drops
- **Harbinger Trophy**: 10% chance - Rare decorative item

## Crafting Recipes

### Entropy Blade
```
   C
 C E C
   D

C = Harbinger's Core (2x)
E = Entropy Essence (4x implicit in pattern)
D = Diamond Sword
```

### Harbinger Armor Set
Each piece requires:
- **Harbinger's Core**: 1-2 pieces depending on armor piece
- **Entropy Essence**: 6 pieces for full coverage
- **Corruption Crystal**: Used in advanced recipes (not yet implemented)

**Helmet Recipe:**
```
E E E
E C E
E E E
```

**Chestplate Recipe:**
```
C   C
E E E
E E E
```

**Leggings Recipe:**
```
E E E
C   C
E   E
```

**Boots Recipe:**
```
C   C
E   E
```

## AI Behavior

### Targeting
- Prioritizes low-health players
- Maintains awareness of all players in 64-block range
- Strategic positioning around arena

### Attack Patterns
- **Phase 1**: Focus on melee with occasional ranged attacks
- **Phase 2**: Mixed combat with frequent teleportation
- **Phase 3**: Aggressive all-out assault with continuous abilities

### Special Behaviors
- Spawns dramatic particle effects during phase transitions
- Boss bar updates in real-time showing current phase
- Maintains aggro on nearest player when current target is unavailable

## Spawning

### Natural Spawn
- Spawns in Void Realm dimension (not yet implemented)
- Rare spawn rate in specific biomes

### Summoning
- Can be summoned at Corruption Altar (not yet implemented)
- Requires specific items and ritual

### Creative Mode
- Use Harbinger Spawn Egg (purple/magenta colors)

## Arena System (Planned)
- Obsidian barriers appear during combat
- Dark sky with purple particles
- Arena cleanup after boss death

## Progression

### Advancement
**"End of Entropy"**
- Granted upon defeating The Harbinger
- Rewards 500 XP
- Shows in chat and toast notification

### Boss Defeat Tracking
- Boss defeat state saved per world (planned)
- Can be re-summoned for farming

## Technical Details

### GeckoLib Integration
- Uses GeckoLib 4.x for animations
- Model: `harbinger.geo.json`
- Animations: `harbinger.animation.json`
- Textures change per phase

### Animation States
- **Idle**: Floating/breathing animation (2 second loop)
- **Walk**: Movement with limb animation (1 second loop)
- **Attack**: Swing animation (0.5 seconds)
- **Death**: Fall and fade animation (2 seconds)

### Performance
- Optimized particle effects
- Efficient AI pathfinding
- Proper cleanup of minions on death

## Balance Notes
- Total HP pool of 500 allows for extended boss fights
- Three phases ensure varied combat experience
- Loot drop rates balanced for replayability (33% for rare items)
- Crafting recipes use reasonable amounts of materials

## Future Enhancements
- Custom Void Bolt projectile entity
- Custom Reality Tear entity/effect
- Custom sound effects for all abilities
- Boss music integration
- Arena barrier system
- Corruption Altar summoning ritual
- Natural spawn mechanics
