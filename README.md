# The Corruption Mod

![Minecraft Version](https://img.shields.io/badge/Minecraft-1.20.1-brightgreen)
![Mod Loader](https://img.shields.io/badge/Mod%20Loader-Fabric-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

**Le Néant s'éveille...** A cosmic horror Minecraft mod featuring corruption mechanics, dimensional exploration, and epic boss battles.

## 📖 Overview

The Corruption Mod introduces a spreading corruption system that transforms the Minecraft world into a dark, twisted realm. Players must fight against the encroaching void, purify corrupted lands, battle corrupted entities, and ultimately face the source of the corruption in an alternate dimension.

## ✨ Features

### 🌍 Corruption System
- **Dynamic Spreading**: Corruption spreads naturally across the world, converting blocks into their corrupted variants
- **Chunk-Based Tracking**: Optimized performance with intelligent chunk processing
- **Multiple Variants**: Corrupted grass, stone, wood, leaves, sand, and even water
- **Visual Effects**: Particle effects and animations indicate corruption presence

### 🛡️ Purification Mechanics
- **Purification Crystals**: Place crystals to create protective zones
- **Sacred Barriers**: Build walls that resist corruption
- **Cleansing Altars**: Perform rituals to cleanse large areas
- **Purification Potions**: Cure corruption effects from players

### 👹 Corrupted Entities
- **Corrupted Mobs**: Zombies, spiders, creepers with enhanced abilities
- **Tainted Animals**: Corrupted cows and other passive mobs
- **Hollowed Villagers**: Former villagers transformed by the void
- **The Stranger**: Mysterious hostile entity
- **Boss: Xynor, Shadow Weaver**: Epic multi-phase boss battle

### 🌌 The Void Realm (Le Néant)
- Custom dimension accessed through Void Portals
- Unique biomes: Blighted Plains, Writhing Forest, Obsidian Wastes, Flesh Valleys
- Ambient atmospheric effects
- Boss arena and special structures

### 🎮 Progression System
- **Advancements**: Track your journey through the corruption
- **Quests**: Complete challenges for unique rewards
- **Loot**: Rare items dropped by corrupted entities and the boss

## 📋 Installation

### Requirements
- Minecraft 1.20.1
- Fabric Loader 0.15.11 or higher
- Fabric API 0.116.7 or higher
- GeckoLib 4.4 or higher

### Steps
1. Install [Fabric Loader](https://fabricmc.net/use/)
2. Download [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
3. Download [GeckoLib](https://www.curseforge.com/minecraft/mc-mods/geckolib)
4. Download The Corruption Mod
5. Place all mod files in your `.minecraft/mods` folder
6. Launch Minecraft with the Fabric profile

## 🎯 Getting Started

### First Steps
1. **Discover Corruption**: Corruption can spawn naturally in your world or be found in the form of Corruption Blocks
2. **Protect Your Base**: Craft Purification Crystals and Sacred Barriers to protect important areas
3. **Gather Resources**: Collect materials for purification items
4. **Fight Corruption**: Battle corrupted entities to gain valuable drops

### Crafting Recipes

**Purification Crystal**
```
 [Diamond]
[Glowstone] [Diamond]
 [Diamond]
```

**Sacred Barrier Stone**
```
[Stone] [Glowstone] [Stone]
[Glowstone] [Diamond] [Glowstone]
[Stone] [Glowstone] [Stone]
```

**Warding Torch**
```
   [Gold Nugget]
      [Torch]
  [Sacred Barrier]
```

### Progression Path
1. **First Contact**: Get corrupted for the first time
2. **Purifier**: Cleanse 100 corrupted blocks
3. **Corruption Hunter**: Defeat 50 corrupted entities
4. **Into the Void**: Enter the Void Realm dimension
5. **Boss Slayer**: Defeat Xynor, Shadow Weaver

## ⚙️ Configuration

The mod includes comprehensive configuration options for:
- **Performance**: Corruption spread rates, chunk processing limits
- **Gameplay**: Entity spawn rates, boss difficulty
- **Visuals**: Particle density, fog effects, animations
- **Audio**: Volume controls for different sound categories
- **Multiplayer**: Sync frequency, packet optimization

Configuration is currently done through the `CorruptionConfig` class (GUI config coming soon).

## 🎮 Commands

### `/corruption spread`
Force corruption to spread immediately (useful for testing).

### `/corruption stats`
Display corruption statistics including:
- Active corrupted chunks
- Total corrupted blocks
- Active corrupted entities
- Corruption spread rate
- Performance metrics
- Memory usage

### `/corruption reset`
Reset corruption statistics (requires operator permission).

## 🔧 Compatibility

### Tested Compatible Mods
- Just Enough Items (JEI)
- Waystones
- Journey Map
- REI (Roughly Enough Items)

### Known Incompatibilities
None currently known. Please report any issues!

## 🌐 Multiplayer Support

The mod is fully multiplayer compatible with:
- Server-side corruption tracking
- Client synchronization
- Per-player progression
- Optimized network packets
- Admin commands for server management

### Server Setup
1. Install the mod on the server
2. Configure settings in the server config file
3. Restart the server
4. Players will automatically download required assets

## 📊 Performance

The mod is designed with performance in mind:
- **Chunk-based processing**: Only processes loaded chunks
- **Configurable tick rates**: Adjust spread frequency
- **Batch updates**: Reduces server lag
- **Memory management**: Automatic cleanup of unloaded data
- **Dirty chunk tracking**: Avoids redundant checks

## 🐛 Bug Reports

Found a bug? Please report it on our [GitHub Issues](https://github.com/adam-KUROPATWA-BUTTE/coruption-mod/issues) page with:
- Minecraft version
- Mod version
- Steps to reproduce
- Crash log (if applicable)

## 📝 Changelog

### Version 1.0.0
- Initial release
- Core corruption spread system
- Purification mechanics
- Corrupted entities
- Void Realm dimension
- Boss fight
- Advancement system
- Performance monitoring
- Bilingual support (EN/FR)

## 👥 Credits

**Development**: Mada  
**Concept**: The Corruption Mod Team  
**Special Thanks**: 
- Fabric Development Team
- GeckoLib Team
- Minecraft Modding Community

## 📜 License

This mod is licensed under the MIT License. See LICENSE file for details.

## 🔗 Links

- **GitHub**: [https://github.com/adam-KUROPATWA-BUTTE/coruption-mod](https://github.com/adam-KUROPATWA-BUTTE/coruption-mod)
- **CurseForge**: Coming Soon
- **Modrinth**: Coming Soon

## 🌟 Support

Enjoying the mod? Consider:
- ⭐ Starring the GitHub repository
- 📢 Sharing with friends
- 💬 Joining our community
- 🐛 Reporting bugs
- 💡 Suggesting features

---

**The Void awaits... Are you ready to face the corruption?**
