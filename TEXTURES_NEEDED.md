# Purification System Textures Documentation

This document describes the required textures for the Purification & Cleansing Systems.

## Block Textures Required

All block textures should be placed in: `src/main/resources/assets/corruptionmod/textures/block/`

### Purification Crystal
- **File**: `purification_crystal.png` (16x16)
- **Description**: A glowing crystalline block with white/gold/blue tones
- **Suggested**: Crystal-like texture with luminescent effect, similar to glowstone but more refined

### Cleansing Altar
- **File**: `cleansing_altar.png` (16x16)
- **Description**: Ornate altar stone with mystical engravings
- **Suggested**: Stone texture with golden/blue runic patterns

### Warding Torch
- **File**: `warding_torch.png` (16x16)
- **Description**: Magical torch with protective glow
- **Suggested**: Torch texture with golden/white flame, distinct from normal torch

### Sacred Barrier (Base)
- **File**: `sacred_barrier.png` (16x16)
- **Description**: Holy barrier block with protective aura
- **Suggested**: White/gold stone with subtle glow or pattern

### Sacred Barrier Stone Variant
- **File**: `sacred_barrier_stone.png` (16x16)
- **Description**: Stone variant of sacred barrier
- **Suggested**: Grey stone with golden engravings

### Sacred Barrier Brick Variant
- **File**: `sacred_barrier_brick.png` (16x16)
- **Description**: Brick variant of sacred barrier
- **Suggested**: Red/brown brick with golden accents

### Sacred Barrier Smooth Variant
- **File**: `sacred_barrier_smooth.png` (16x16)
- **Description**: Smooth variant of sacred barrier
- **Suggested**: Smooth polished stone with subtle golden highlights

## Item Textures Required

All item textures should be placed in: `src/main/resources/assets/corruptionmod/textures/item/`

### Purification Potions
- **File**: `purification_potion_weak.png` (16x16)
  - Weak purification potion
  - Suggested: Bottle with light golden/white liquid
  
- **File**: `purification_potion.png` (16x16)
  - Standard purification potion
  - Suggested: Bottle with brighter golden/white liquid
  
- **File**: `purification_potion_strong.png` (16x16)
  - Strong purification potion
  - Suggested: Bottle with intense glowing golden/white liquid

## Temporary Solution

For development/testing, you can use placeholder textures or copy similar textures from vanilla Minecraft:
- Use glowstone texture for purification_crystal
- Use stone texture for sacred barriers
- Use torch texture for warding_torch
- Use potion textures for purification potions

## Creating Textures

If you want to create custom textures:
1. Use a 16x16 pixel canvas
2. Save as PNG with transparency where needed
3. Follow Minecraft's texture style (blocky, pixel art)
4. Use colors that match the theme:
   - Purification: Gold, white, light blue
   - Sacred: White, gold, silver
   - Protection: Blue, purple, gold accents
