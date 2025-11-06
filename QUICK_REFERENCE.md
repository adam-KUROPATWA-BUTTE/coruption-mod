# Quick Reference: Corrupted Entities Implementation

## 📁 File Structure

### Core Effect System
```
src/main/java/com/corruptionmod/
├── ModEffects.java                              # Effect registry
└── effect/
    └── CorruptionEffect.java                    # 4-level corruption status effect
```

### Entity System
```
src/main/java/com/corruptionmod/
├── ModEntities.java                             # Entity registry and registration
└── entity/
    ├── ai/
    │   └── ApplyCorruptionEffectGoal.java       # Custom AI for corruption spreading
    └── corrupted/
        ├── CorruptedZombieEntity.java           # Block corruption + trails
        ├── CorruptedSpiderEntity.java           # Corrupting webs
        ├── CorruptedCreeperEntity.java          # Corruption explosion
        ├── TaintedCowEntity.java                # Aggressive tainted animal
        └── HollowedVillagerEntity.java          # Variable corruption behavior
```

### Resources
```
src/main/resources/assets/corruptionmod/
├── lang/
│   └── en_us.json                               # Entity & effect translations
└── loot_tables/entities/
    ├── corrupted_zombie.json                    # Zombie drops
    ├── corrupted_spider.json                    # Spider drops
    ├── corrupted_creeper.json                   # Creeper drops
    ├── tainted_cow.json                         # Cow drops
    └── hollowed_villager.json                   # Villager drops
```

### Documentation
```
.
├── CORRUPTED_ENTITIES_IMPLEMENTATION.md         # Technical documentation
├── IMPLEMENTATION_CHECKLIST.md                  # Feature checklist
├── PR_DESCRIPTION.md                            # PR summary
└── QUICK_REFERENCE.md                           # This file
```

## 🎯 Quick Access by Entity

### Corrupted Zombie
- **File**: `entity/corrupted/CorruptedZombieEntity.java`
- **Health**: 25 HP
- **Special**: Block corruption, trails, Level II corruption effect
- **Loot**: `loot_tables/entities/corrupted_zombie.json`

### Corrupted Spider
- **File**: `entity/corrupted/CorruptedSpiderEntity.java`
- **Health**: 20 HP
- **Special**: Corrupting webs, web-based corruption spread
- **Loot**: `loot_tables/entities/corrupted_spider.json`

### Corrupted Creeper
- **File**: `entity/corrupted/CorruptedCreeperEntity.java`
- **Health**: 22 HP
- **Special**: Explosion corruption (4.0 radius)
- **Loot**: `loot_tables/entities/corrupted_creeper.json`

### Tainted Cow
- **File**: `entity/corrupted/TaintedCowEntity.java`
- **Health**: 15 HP
- **Special**: Aggressive behavior (example tainted animal)
- **Loot**: `loot_tables/entities/tainted_cow.json`

### Hollowed Villager
- **File**: `entity/corrupted/HollowedVillagerEntity.java`
- **Health**: 18 HP
- **Special**: Variable behavior based on corruption level (1-4)
- **Loot**: `loot_tables/entities/hollowed_villager.json`

## 🔧 Key Systems

### Corruption Effect
- **Location**: `effect/CorruptionEffect.java`
- **Registration**: `ModEffects.register()`
- **Levels**: I-IV (amplifiers 0-3)
- **Damage**: 0.5 → 1.0 → 1.5 → 2.0 per tick cycle
- **Particles**: Portal particles (count scales with level)

### Block Corruption Mapping
```java
GRASS_BLOCK    → CORRUPTED_GRASS
DIRT           → CORRUPTED_DIRT
STONE          → CORRUPTED_STONE
SAND           → CORRUPTED_SAND
LEAVES (all)   → WITHERED_LEAVES
```

### AI Goals
- **ApplyCorruptionEffectGoal**: Custom goal for corruption spreading
  - Range: 4 blocks
  - Duration: 10 seconds per application
  - Configurable level and interval

## 🚀 Testing Commands

Once the mod is built, use these commands to test:

```mcfunction
# Summon entities
/summon corruptionmod:corrupted_zombie ~ ~ ~
/summon corruptionmod:corrupted_spider ~ ~ ~
/summon corruptionmod:corrupted_creeper ~ ~ ~
/summon corruptionmod:tainted_cow ~ ~ ~
/summon corruptionmod:hollowed_villager ~ ~ ~

# Apply corruption effect
/effect give @p corruptionmod:corruption 60 0  # Level I
/effect give @p corruptionmod:corruption 60 1  # Level II
/effect give @p corruptionmod:corruption 60 2  # Level III
/effect give @p corruptionmod:corruption 60 3  # Level IV

# Clear corruption
/effect clear @p corruptionmod:corruption
# Or drink milk bucket
```

## 📊 Entity Comparison Table

| Entity              | HP | ATK | Speed | Special Ability                |
|---------------------|----|----|-------|--------------------------------|
| Corrupted Zombie    | 25 | 4  | 0.25  | Block corruption + trails      |
| Corrupted Spider    | 20 | 3  | 0.35  | Corrupting webs                |
| Corrupted Creeper   | 22 | -  | 0.27  | Explosion corruption (R=4.0)   |
| Tainted Cow         | 15 | 2  | 0.22  | Aggressive behavior            |
| Hollowed Villager   | 18 | 2.5| 0.20  | Variable behavior (L1-4)       |

## 🎨 Visual Effects Summary

| Entity              | Particle Type    | Frequency       |
|---------------------|------------------|-----------------|
| All Corrupted       | Portal (purple)  | Random (1/3-1/5)|
| Corruption Effect   | Portal (purple)  | Every 10 ticks  |
| Block Corruption    | Smoke            | On corrupt      |
| Creeper Explosion   | Large Smoke      | On death        |

## 🔗 Integration Points

1. **Main Initialization**: `CorruptionMod.onInitialize()`
   - Calls `ModEffects.register()`
   - Calls `ModEntities.register()`

2. **Entity Registration**: `ModEntities.register()`
   - Creates EntityType with FabricEntityTypeBuilder
   - Registers attributes with FabricDefaultAttributeRegistry

3. **Effect Registration**: `ModEffects.register()`
   - Registers to Registry.STATUS_EFFECT

4. **Translations**: `lang/en_us.json`
   - 6 new translation keys added

## 📖 Documentation Files

- **CORRUPTED_ENTITIES_IMPLEMENTATION.md**: Full technical documentation
- **IMPLEMENTATION_CHECKLIST.md**: Complete feature checklist
- **PR_DESCRIPTION.md**: Pull request summary
- **QUICK_REFERENCE.md**: This quick reference guide

---
*For detailed implementation information, see CORRUPTED_ENTITIES_IMPLEMENTATION.md*
