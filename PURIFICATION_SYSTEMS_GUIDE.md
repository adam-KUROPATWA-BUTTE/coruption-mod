# Purification & Cleansing Systems - Implementation Guide

This document provides a comprehensive guide to the implemented purification and cleansing systems for the Corruption Mod.

## Overview

The purification systems provide players with tools to resist, slow, and remove corruption. These systems integrate seamlessly with the existing corruption spread mechanics.

## Implemented Features

### 1. Purification Crystal

**Block Class**: `PurificationCrystalBlock.java`
**Manager**: `PurificationManager.java`

**Features**:
- Creates a 32-block radius purification zone
- Automatically cleanses one corrupted block every 10 seconds within range
- Prevents corruption spread entirely within protected area
- Visual particles (requires textures)

**Recipe**:
```
D G D
G E G
D G D

D = Diamond
G = Glowstone
E = Emerald
```

**Usage**: Place the crystal in an area to protect. It will automatically work to cleanse corruption over time.

---

### 2. Cleansing Altar & Ritual System

**Block Class**: `CleansingAltarBlock.java`
**Manager**: `CleansingRitualManager.java`

**Structure Requirements**:
- Cleansing Altar in center
- 4 Sacred Barrier blocks at corners (3x3 pattern, one block below altar)
- Optional: 4 Gold Blocks at cardinal directions for increased success rate

**Ritual Offerings**:
- Required: 4 Diamonds, 2 Gold Ingots, 1 Emerald
- These are consumed when ritual is performed

**Success Mechanics**:
- Base success rate: 70%
- Each gold block at cardinal directions: +5% success rate
- Maximum success rate: 90%
- On success: Purifies up to 25 corrupted blocks in 16-block radius
- On failure: Offerings are still consumed, particles/sounds indicate failure

**Recipe** (Altar):
```
G S G
S D S
G S G

G = Gold Block
S = Stone
D = Diamond Block
```

**Usage**:
1. Build the structure with altar and 4 sacred barriers
2. Optionally place gold blocks at cardinal directions
3. Gather required offerings
4. Right-click the altar with offerings in inventory
5. Ritual performs with chance-based success

---

### 3. Warding Torches

**Block Classes**: `WardingTorchBlock.java`, `WardingWallTorchBlock.java`

**Features**:
- Creates 8-block radius protection zone (smaller than crystal)
- Slows corruption spread by reducing chance
- Stackable: Multiple torches increase protection
  - 1 torch: 25% protection
  - 2 torches: 50% protection
  - 3 torches: 75% protection
  - 4+ torches: 100% protection (full immunity)
- Can be placed on floor or walls
- Visual enchant particles distinguish from normal torches

**Recipe**:
```
  G
G S G
  S

G = Glowstone Dust
S = Stick
Result: 4 Warding Torches
```

**Usage**: Place torches around areas you want to protect. Stack multiple torches for stronger protection.

---

### 4. Sacred Barrier Blocks

**Block Class**: `SacredBarrierBlock.java`

**Features**:
- Corruption cannot spread through these blocks
- Use to create safe zones and boundaries
- 4 decorative variants: Base, Stone, Brick, Smooth
- Emits END_ROD particles for mystical effect
- High blast resistance (1200.0)

**Recipe** (Base):
```
S D S
D G D
S D S

S = Stone
D = Diamond
G = Glowstone
Result: 4 Sacred Barriers
```

**Variant Recipes** (Shapeless):
- Sacred Barrier + Stone = Stone Variant
- Sacred Barrier + Brick = Brick Variant
- Sacred Barrier + Smooth Stone = Smooth Variant

**Usage**: Build walls or enclosures around areas to prevent corruption from crossing the barrier.

---

### 5. Purification Potions

**Item Class**: `PurificationPotionItem.java`
**Effect Class**: `PurificationEffect.java`

**Three Tiers**:

1. **Weak Purification Potion**
   - Duration: 30 seconds
   - Thrown radius: 3 blocks
   - Purifies: 10 blocks
   
2. **Purification Potion** (Normal)
   - Duration: 60 seconds
   - Thrown radius: 4 blocks
   - Purifies: 20 blocks
   
3. **Strong Purification Potion**
   - Duration: 90 seconds
   - Thrown radius: 5 blocks
   - Purifies: 30 blocks

**Brewing Recipes**:
```
Awkward Potion + Glowstone Dust = Weak Purification Potion
Weak Purification Potion + Golden Carrot = Purification Potion
Purification Potion + Diamond = Strong Purification Potion
```

**Usage**:
- Drink: Grants Purification effect (removes corruption from player)
- Throw: Purifies corrupted blocks in area of effect
- Creates HAPPY_VILLAGER particles when purifying blocks

---

## Technical Integration

### Corruption Spread System Integration

The purification systems integrate with `WorldCorruptionTicker.java`:

1. **Crystal Protection**: Blocks all corruption spread within 32-block radius
2. **Warding Protection**: Reduces corruption chance by 25% per torch (up to 100%)
3. **Barrier Blocking**: Sacred Barriers cannot be corrupted and block spread

**Code Flow**:
```java
// In WorldCorruptionTicker.attemptSpreadFrom()
if (PurificationManager.isProtectedByCrystal(world, pos)) {
    continue; // Skip this position
}

float wardingProtection = PurificationManager.getWardingProtection(world, pos);
chance *= (1.0f - wardingProtection); // Reduce corruption chance

if (SacredBarrierBlock.isBarrier(block)) {
    return false; // Cannot corrupt barriers
}
```

### Block Registration

All blocks registered in `ModBlocks.java`:
- PURIFICATION_CRYSTAL
- CLEANSING_ALTAR
- WARDING_TORCH / WARDING_WALL_TORCH
- SACRED_BARRIER (+ variants)

### Item Registration

All items registered in `ModItems.java`:
- Block items for all blocks
- PURIFICATION_POTION_WEAK
- PURIFICATION_POTION
- PURIFICATION_POTION_STRONG

### Effect Registration

Status effect registered in `ModEffects.java`:
- PURIFICATION effect

### Main Initialization

Updated `CorruptionMod.java` to register:
- ModBlocks.register()
- ModItems.register()
- ModEffects.register()
- PurificationManager.register()

---

## Resource Files

### Recipes (data/corruptionmod/recipes/)
- ✅ purification_crystal.json
- ✅ cleansing_altar.json
- ✅ warding_torch.json
- ✅ sacred_barrier.json (+ variants)
- ✅ brewing_purification_potion_weak.json
- ✅ brewing_purification_potion.json
- ✅ brewing_purification_potion_strong.json

### Blockstates (assets/corruptionmod/blockstates/)
- ✅ All block blockstates created

### Models (assets/corruptionmod/models/)
- ✅ All block models created
- ✅ All item models created

### Textures
- ⚠️ Texture PNG files need to be created
- 📄 See TEXTURES_NEEDED.md for specifications

---

## Game Balance

### Early Game
- **Warding Torches**: Accessible with glowstone dust
- **Weak Purification Potions**: Basic protection

### Mid Game
- **Purification Crystals**: Require diamonds and emeralds
- **Sacred Barriers**: Good for building safe zones
- **Normal Purification Potions**: Better effectiveness

### Late Game
- **Cleansing Altar**: Expensive but powerful
- **Strong Purification Potions**: Most effective
- **Multiple Crystals**: Large scale protection

### Resource Costs Summary
- Warding Torch (4): 3 Glowstone Dust, 2 Sticks
- Purification Crystal: 4 Diamonds, 4 Glowstone, 1 Emerald
- Sacred Barrier (4): 4 Diamonds, 4 Glowstone, 4 Stone
- Cleansing Altar: 4 Gold Blocks, 4 Stone, 1 Diamond Block
- Ritual Offering: 4 Diamonds, 2 Gold Ingots, 1 Emerald

---

## Future Enhancements

Potential additions for future updates:

1. **Purified Block States**
   - Temporary immunity after cleansing
   - Visual distinction with particles
   - Time-based immunity decay

2. **Advanced Rituals**
   - Multiple ritual types
   - More complex structures
   - Special offerings for enhanced effects

3. **Enchantments**
   - Purification enchantment for tools
   - Corruption resistance armor enchantments

4. **Dimensions**
   - Purification mechanics in The Void dimension
   - Special purification challenges

---

## Testing Checklist

- [ ] Purification Crystal places and removes correctly
- [ ] Crystal prevents corruption in radius
- [ ] Cleansing Altar structure validation works
- [ ] Ritual success/failure mechanics function
- [ ] Ritual consumes offerings correctly
- [ ] Warding Torches place on floor and walls
- [ ] Multiple torches stack protection
- [ ] Sacred Barriers block corruption spread
- [ ] Potions grant status effect when drunk
- [ ] Thrown potions purify blocks
- [ ] All recipes craftable
- [ ] All translations display correctly
- [ ] All blocks have proper models

---

## Credits

Implementation by GitHub Copilot for adam-KUROPATWA-BUTTE/coruption-mod
Part of Issue #6: Purification & Cleansing Systems
