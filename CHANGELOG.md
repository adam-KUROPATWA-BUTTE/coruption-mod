# Changelog

All notable changes to the Corruption Mod will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2024-01-XX

### Added

#### Core Systems
- Corruption spread system with chunk-based tracking
- Purification mechanics with crystals, barriers, and altars
- Performance monitoring system with `/corruption stats` command
- Comprehensive configuration system for all gameplay aspects
- Advancement progression system with 6 achievements

#### Blocks
- Corruption Block - Source of corruption
- Corrupted variants: Grass, Dirt, Stone, Wood, Sand, Leaves, Water
- Purification Crystal - Protects areas from corruption
- Sacred Barriers - Corruption-resistant walls (Stone, Brick, Smooth variants)
- Cleansing Altar - Performs purification rituals
- Warding Torch - Protective light source
- Void Portal Frame - Gateway to Void Realm
- Void Stone, Corruption Flesh, Obsidian Crust, Twisted Log, Blighted Grass

#### Entities
- Corrupted mobs: Zombie, Spider, Creeper
- Tainted Cow - Corrupted passive mob
- Hollowed Villager - Corrupted villager variant
- The Stranger - Hostile mysterious entity
- Xynor, Shadow Weaver - Epic boss with multi-phase combat

#### Dimensions
- Le Néant (The Void Realm) - Custom dimension
- Four unique biomes: Blighted Plains, Writhing Forest, Obsidian Wastes, Flesh Valleys
- Custom ambient effects and atmosphere

#### Items
- Heart of Void - Boss drop and crafting material
- Void Key - Portal activation item
- Purification Potions - Weak, Normal, and Strong variants
- All corruption and purification blocks as items

#### Effects
- Corruption Effect - Applied by corrupted entities and blocks
- Purification Effect - Removes corruption from players

#### Sounds
- 20+ custom sound events including:
  - Ambient sounds (void theme, whispers, heartbeat)
  - Block sounds (corruption spread, break, place, step)
  - Entity sounds (hurt, death, ambient)
  - Boss sounds (roar, attack)
  - Purification sounds
  - Music tracks for Void Realm and boss fight
- Full subtitle support in English and French

#### Commands
- `/corruption spread` - Force immediate corruption spread
- `/corruption stats` - Display performance and corruption statistics
- `/corruption reset` - Reset statistics (admin only)

#### Advancements
- **The Corruption** (Root) - Discover corruption
- **First Contact** - Get corrupted for the first time
- **Purifier** - Cleanse 100 corrupted blocks
- **Corruption Hunter** - Defeat 50 corrupted entities
- **Into the Void** - Enter the Void Realm
- **Boss Slayer** - Defeat Xynor, Shadow Weaver

#### Localization
- Full English (en_us) translations
- Complete French (fr_fr) translations
- All blocks, items, entities, effects, biomes, advancements, and sounds

#### Visual Effects
- Particle system with 8+ different effects
- Corruption spread particles (portal, smoke)
- Purification particles (sparkles, glowing orbs)
- Ambient corruption particles (spores, wisps)
- Crystal glow effects
- Boss attack particles
- Entity death particles
- Void realm atmospheric particles
- Configurable particle density for performance

#### Documentation
- Comprehensive README with installation and features
- Detailed CONFIGURATION guide with presets
- In-code documentation for all public methods
- Crafting recipe documentation

### Performance Optimizations
- Chunk-based corruption tracking with HashMap
- Configurable tick rates for spread (20-40 seconds default)
- Batch block updates to reduce lag
- Dirty chunk tracking to avoid redundant checks
- Automatic cleanup of unloaded chunk data
- Memory monitoring utilities
- Performance statistics tracking
- Optimized network packets for multiplayer

### Multiplayer Support
- Server-side corruption tracking
- Client-side synchronization
- Per-player advancement progression
- Delta compression for updates
- Configurable sync frequency
- Admin commands with permission checks

### Changed
- N/A (Initial release)

### Deprecated
- N/A (Initial release)

### Removed
- N/A (Initial release)

### Fixed
- N/A (Initial release)

### Security
- No known security issues

## [Unreleased]

### Planned Features
- File-based configuration system (.toml)
- In-game configuration GUI
- In-game guide book (Tome of Corruption)
- Additional advancement achievements:
  - Quest Master - Complete all quests
  - Full Set - Obtain complete Harbinger armor
  - Collector - Obtain all unique items
- Boss armor and weapons
- Quest system implementation
- NPC dialogue system
- Custom fog renderer for corrupted areas
- Enhanced block animations
- GeckoLib entity animations
- Music disc items
- Additional sound effects
- Performance profiling tools
- Debug overlay (F3 integration)
- Data backup and recovery system
- More corrupted block variants
- Additional purification methods
- Corruption-themed structures

### Known Issues
- Build system requires specific fabric-loom version configuration
- Configuration currently requires code modification
- Some advancements use placeholder triggers pending implementation
- Guide book not yet implemented

---

## Version History

- **1.0.0** - Initial release with core corruption mechanics, entities, dimension, sounds, and advancements

## Links
- [GitHub Repository](https://github.com/adam-KUROPATWA-BUTTE/coruption-mod)
- [Issue Tracker](https://github.com/adam-KUROPATWA-BUTTE/coruption-mod/issues)

## Credits
- **Development**: Mada
- **Concept**: The Corruption Mod Team
- **Framework**: Fabric, GeckoLib
