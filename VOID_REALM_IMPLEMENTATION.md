# Void Realm Dimension Implementation

## Overview
The Void Realm (Le Néant) is a corrupted dimension with four distinct biomes, custom blocks, and portal mechanics.

## Dimension Structure

### Dimension Type
- **ID**: `corruptionmod:le_neant`
- **Properties**:
  - No natural light (ambient_light: 0.1)
  - Fixed time at night (18000 ticks)
  - No skylight, no ceiling
  - Height: 0-256 blocks
  - Unique visual effects (purple fog, dark sky)

### Biomes

#### 1. Blighted Plains
- **Description**: Corrupted wasteland with sparse vegetation
- **Colors**: Purple-gray sky, dark purple grass
- **Features**: Corruption flesh patches
- **Mood**: Desolate and eerie

#### 2. Writhing Forest
- **Description**: Twisted forest with corrupted trees
- **Colors**: Very dark sky, minimal foliage
- **Features**: Twisted Log trees with withered leaves
- **Mood**: Dense and claustrophobic

#### 3. Obsidian Wastes
- **Description**: Volcanic-like obsidian landscape
- **Colors**: Nearly black sky, red water tints
- **Features**: Obsidian crust ore deposits
- **Mood**: Hot and oppressive
- **Sounds**: Basalt Deltas ambient sounds

#### 4. Flesh Valleys
- **Description**: Organic, disturbing terrain
- **Colors**: Reddish sky, flesh-colored terrain
- **Features**: Corruption flesh blocks, organic structures
- **Mood**: Unsettling and organic

## Custom Blocks

### Dimension Base Blocks
- **Void Stone**: Primary stone block, emits faint portal particles
  - Strength: 2.5f, Resistance: 12.0f
  - Luminance: 1 (very faint glow)

- **Corruption Flesh**: Organic corrupted block
  - Slows entities walking on it (0.7x speed)
  - Emits crimson spore particles
  - Soft texture and sound

- **Obsidian Crust**: Volcanic obsidian variant
  - Very high blast resistance (1200.0f)
  - Emits smoke particles
  - Luminance: 3

- **Twisted Log**: Corrupted wood for trees
  - Can be oriented on any axis (pillar block)
  - Used in Writhing Forest biome

- **Blighted Grass**: Dimension grass variant
  - Spreads corruption slowly
  - Emits mycelium particles
  - Can be placed on void stone

## Portal Mechanics

### Void Key Item
- **Function**: Activates portals and teleports between dimensions
- **Durability**: 64 uses
- **Usage**:
  - Right-click to teleport between Overworld and Void Realm
  - Right-click on portal frame to activate portal

### Portal Construction
- **Frame**: 4x5 minimum structure using Void Portal Frame blocks
- **Activation**: Use Void Key on frame
- **Travel**: Walk through activated portal or use Void Key directly

### Teleportation
- **Overworld → Void Realm**: Spawn at Y=64 with matching X/Z coordinates
- **Void Realm → Overworld**: Return to original position
- **Effects**: Portal travel sound, visual effects

## World Generation

### Chunk Generator
- **Type**: Custom `VoidRealmChunkGenerator`
- **Terrain**: Relatively flat with gentle hills
- **Base Height**: ~64 blocks with sine wave variation
- **Surface**: Varies by biome (grass or flesh)
- **Subsurface**: 4+ layers of void stone

### Biome Distribution
- **Source**: `VoidRealmBiomeSource`
- **Method**: Coordinate-based hash distribution
- **Size**: ~64 block regions per biome
- **Variety**: All four biomes mixed throughout dimension

### World Features
1. **Corruption Flesh Patches**: Random patches in Blighted Plains and Flesh Valleys
2. **Twisted Trees**: Forest structures in Writhing Forest
3. **Obsidian Crust Ore**: Underground veins in Obsidian Wastes

## Ambient Effects

### Particle Effects
- **Portal Particles**: Spawn around players (3 per second)
- **Smoke Particles**: Occasional ambient effect
- **Block Particles**: Emitted by specific blocks

### Sound Effects
- **Ambient**: Cave sounds with lowered pitch (0.8x)
- **Portal**: Activation and travel sounds
- **Biome-Specific**: Basalt Deltas sounds in Obsidian Wastes

### Visual Effects
- **Sky Color**: Dark purple (varies by biome)
- **Fog Color**: Very dark purple
- **Water**: Purple-tinted corrupted water
- **Lighting**: Dim ambient light (0.1)

## Technical Implementation

### Registry Keys
- **Dimension World**: `corruptionmod:le_neant`
- **Dimension Type**: `corruptionmod:le_neant`
- **Biomes**:
  - `corruptionmod:blighted_plains`
  - `corruptionmod:writhing_forest`
  - `corruptionmod:obsidian_wastes`
  - `corruptionmod:flesh_valleys`

### Data Pack Files
- Dimension type definition: `data/corruptionmod/dimension_type/le_neant.json`
- Dimension definition: `data/corruptionmod/dimension/le_neant.json`
- Biome definitions: `data/corruptionmod/worldgen/biome/*.json`
- Features: `data/corruptionmod/worldgen/configured_feature/*.json`
- Placements: `data/corruptionmod/worldgen/placed_feature/*.json`

### Class Structure
```
com.corruptionmod
├── ModBlocks - Block registration
├── ModItems - Item registration (Void Key)
├── ModDimensions - Dimension registration
├── ModWorldGen - World generation setup
├── block/
│   ├── VoidStoneBlock
│   ├── CorruptionFleshBlock
│   ├── ObsidianCrustBlock
│   ├── TwistedLogBlock
│   └── BlightedGrassBlock
├── item/
│   └── VoidKeyItem
├── world/
│   ├── ModBiomes
│   ├── VoidRealmChunkGenerator
│   ├── VoidRealmBiomeSource
│   └── VoidRealmAmbientEffects
└── util/
    └── VoidPortalHelper
```

## Future Enhancements

### Planned Features
1. **Portal Blocks**: Actual portal blocks that trigger teleportation on contact
2. **Structure Generation**: Temples, ruins, and corruption sources
3. **Dimension-Specific Mobs**: Void creatures and corrupted entities
4. **Advanced Terrain**: More varied height maps, caves, ravines
5. **Biome Transitions**: Smooth blending between biomes
6. **Resource Generation**: Unique ores and materials
7. **Weather Effects**: Void storms and corruption rain
8. **Sky Rendering**: Custom sky with void effects

### Integration Points
- **Corruption Mechanics**: Dimension blocks can spread to Overworld
- **Boss Fights**: Xynor can spawn in specific structures
- **Quest System**: Portal activation tied to progression
- **Loot Tables**: Unique items from dimension blocks and structures

## Configuration

### Dimension Properties
All dimension properties are configured via JSON files in the data pack:
- Modify `dimension_type/le_neant.json` to change dimension behavior
- Edit biome JSON files to adjust colors, spawns, and features
- Update feature files to add/remove world generation elements

### Balance Adjustments
- Block hardness and resistance in respective block classes
- Portal durability in `VoidKeyItem`
- Particle spawn rates in `VoidRealmAmbientEffects`
- Biome distribution in `VoidRealmBiomeSource`

## Known Limitations

1. **Simplified Chunk Generation**: Uses basic noise for terrain
2. **Basic Biome Distribution**: Simple hash-based distribution
3. **No Portal Blocks**: Currently only teleportation via Void Key
4. **Limited Features**: Few world generation features per biome
5. **No Structures**: No generated structures yet
6. **Placeholder Textures**: Simple colored textures need replacement

## Testing Recommendations

1. **Dimension Access**: Use `/execute in corruptionmod:le_neant` to test
2. **Biome Variety**: Explore multiple regions to see all biomes
3. **Portal Mechanics**: Test Void Key in both dimensions
4. **Block Behavior**: Test particles, sounds, and special properties
5. **Performance**: Check chunk generation performance
6. **Integration**: Test with existing corruption mechanics

## Credits

- Dimension system: Minecraft 1.20.1 dimension API
- Biome system: Minecraft worldgen system
- Teleportation: Fabric Dimensions API
- Textures: Placeholder (need artist replacement)
