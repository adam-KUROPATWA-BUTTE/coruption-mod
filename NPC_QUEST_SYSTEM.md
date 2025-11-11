# NPC, Dialogue, and Quest System

This document describes the NPC, dialogue, and quest system implemented for the Corruption Mod.

## Overview

The system adds three interactive NPCs with full dialogue trees and a complete 5-quest chain that guides players through the corruption storyline. All content is available in both English and French.

## NPCs

### Elder Maren
- **Role:** Quest giver and lore keeper
- **Appearance:** Old villager with grey robes and glowing purple eyes
- **Location:** Spawns in villages near corrupted areas
- **Quests:** Gives Quest 1, 2, and 4

### Kael the Wanderer
- **Role:** Combat trainer and corruption survivor
- **Appearance:** Worn leather armor with corruption scars
- **Location:** Roams between villages and corrupted zones
- **Quests:** Gives Quest 3

### The Hollow One
- **Role:** Mysterious entity and late-game guide
- **Appearance:** Shadowy figure with void particles
- **Location:** Only appears in Void Realm dimension
- **Quests:** Gives Quest 5

## Quest Chain

### Quest 1: "The First Infection"
- Collect a corrupted block sample
- Return to Elder Maren
- **Rewards:** 50 XP, 16 Bread, 32 Gold

### Quest 2: "Echoes of the Past"
- Explore the Ancient Temple
- Find the Corruption Tome
- Return to Elder Maren
- **Rewards:** 100 XP, 64 Gold, Purification Crystal recipe

### Quest 3: "The Cleansing"
- Craft a Purification Crystal
- Cleanse 10 corrupted blocks
- Defeat 10 corrupted enemies
- **Rewards:** 150 XP, 8 Warding Torches, 3 Purification Potions

### Quest 4: "Into the Void"
- Craft a Void Portal Frame
- Activate the portal to Void Realm
- Explore the Void Realm
- Return safely
- **Rewards:** 200 XP, 16 Void Stone, 8 Corruption Essence

### Quest 5: "Heart of Darkness"
- Find the Harbinger's Arena
- Defeat The Harbinger boss
- Obtain Harbinger's Core
- **Rewards:** 500 XP, Entropy Blade recipe, Harbinger Armor recipes, Achievement

## System Architecture

### Core Components

#### NPC System (`com.corruptionmod.npc`)
- `BaseNPCEntity`: Abstract base class for all NPCs
  - Handles player interaction (right-click)
  - Implements pathfinding and idle behavior
  - Cannot despawn or be affected by peaceful mode

#### Dialogue System (`com.corruptionmod.dialogue`)
- `DialogueManager`: Manages NPC dialogues
  - Loads dialogue from JSON files
  - Tracks active dialogues per player
  - Supports conditions and branching
- `DialogueNode`: Represents dialogue nodes with options
  - Multi-language support (EN/FR)
  - Optional quest integration
  - Conditional visibility

#### Quest System (`com.corruptionmod.quest`)
- `QuestManager`: Handles quest tracking and progression
  - Loads quests from JSON files
  - Tracks active and completed quests per player
  - Manages quest rewards and XP
  - Saves/loads quest progress with NBT
- `Quest`: Quest data structure
  - Multi-language names and descriptions
  - Multiple objectives with progress tracking
  - Item and XP rewards
  - Quest prerequisites and chains

### Data Files

#### Dialogues (`/data/corruptionmod/dialogues/`)
- `elder_maren.json`: Elder Maren's dialogue tree
- `kael.json`: Kael's dialogue tree
- `hollow_one.json`: The Hollow One's dialogue tree

Each dialogue file contains:
- Node structure with unique IDs
- Bilingual text (EN/FR)
- Dialogue options with branching
- Quest integration points
- Optional conditions

#### Quests (`/data/corruptionmod/quests/`)
- `quest_1_first_infection.json`
- `quest_2_echoes_past.json`
- `quest_3_cleansing.json`
- `quest_4_into_void.json`
- `quest_5_heart_darkness.json`

Each quest file contains:
- Quest ID and bilingual name/description
- Objectives with types and targets
- Reward items and XP
- Quest prerequisites and chain

#### Localization (`/assets/corruptionmod/lang/`)
- `en_us.json`: English translations
- `fr_fr.json`: French translations

Contains all entity names, quest names, descriptions, and objectives.

## Commands

### Quest Commands (Operator Level 2 required)

```
/quest start <player> <questId>
```
Start a quest for a player.

```
/quest complete <player> <questId>
```
Complete a quest for a player (gives rewards).

```
/quest list <player>
```
List active and completed quests for a player.

### Examples
```
/quest start @p quest_1_first_infection
/quest complete @p quest_1_first_infection
/quest list @p
```

## Interaction

Players interact with NPCs by right-clicking them. This opens a dialogue interface (to be implemented) that shows:
- NPC name and dialogue text in player's language
- Available dialogue options
- Quest status indicators

## Integration Points

The system integrates with:
- **Existing entities:** Corrupted mobs for quest objectives
- **Existing blocks:** Corruption blocks for quest objectives
- **Existing dimensions:** Void Realm for Quest 4 and 5
- **Existing items:** Purification crystals, void portal frames, etc.

## Future Enhancements

Planned features for future updates:
- Graphical dialogue UI with NPC portraits
- Quest log GUI accessible via keybind
- Quest tracker HUD showing active objectives
- Quest notification toasts
- Particle effects above NPCs indicating quest status
- Achievement/advancement integration
- Quest journal item (written book)
- Daily/repeatable quests
- Reputation system

## Technical Notes

- NPCs are persistent and won't despawn
- Quest progress is saved per player using NBT
- All dialogues support conditional branching
- Quests can have multiple objectives of different types
- The system is fully data-driven via JSON files
- Language selection follows Minecraft's language settings

## For Developers

### Adding a New NPC

1. Create entity class extending `BaseNPCEntity`
2. Implement `createAttributes()` and `openDialogue()` methods
3. Register in `ModEntities`
4. Create dialogue JSON in `/data/corruptionmod/dialogues/`
5. Add localization strings to language files

### Adding a New Quest

1. Create quest JSON in `/data/corruptionmod/quests/`
2. Define objectives with appropriate types
3. Specify rewards and XP
4. Add quest to NPC dialogue as needed
5. Add localization strings to language files

### Objective Types

Supported objective types:
- `collect_item`: Collect specific items
- `kill_entity`: Kill specific entities
- `talk_to_npc`: Talk to an NPC
- `explore_structure`: Find a structure
- `craft_item`: Craft an item
- `cleanse_blocks`: Cleanse corrupted blocks
- `activate_portal`: Activate a portal
- `explore_dimension`: Visit a dimension
- `return_dimension`: Return from a dimension
- `find_location`: Find a specific location

## Credits

Implementation follows Minecraft modding best practices and Fabric API conventions.
