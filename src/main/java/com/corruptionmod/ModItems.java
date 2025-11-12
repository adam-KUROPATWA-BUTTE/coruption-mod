package com.corruptionmod;

import com.corruptionmod.item.*;
import com.corruptionmod.potion.PurificationPotionItem;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

/**
 * Registers all mod items including block items, potions, and boss items.
 */
public class ModItems {
    // Purification potions
    public static Item PURIFICATION_POTION_WEAK;
    public static Item PURIFICATION_POTION;
    public static Item PURIFICATION_POTION_STRONG;
    
    // Void Realm items
    public static Item VOID_KEY;
    
    // Harbinger boss items
    public static Item HARBINGER_CORE;
    public static Item ENTROPY_ESSENCE;
    public static Item CORRUPTION_CRYSTAL;
    public static Item ENTROPY_BLADE;
    public static Item HARBINGER_HELMET;
    public static Item HARBINGER_CHESTPLATE;
    public static Item HARBINGER_LEGGINGS;
    public static Item HARBINGER_BOOTS;
    public static Item HARBINGER_TROPHY;
    public static Item HARBINGER_SPAWN_EGG;
    
    public static void register() {
        // Register block items for all blocks
        registerBlockItem("corruption_block", ModBlocks.CORRUPTION_BLOCK);
        registerBlockItem("corruption_altar", ModBlocks.CORRUPTION_ALTAR);
        registerBlockItem("void_portal_frame", ModBlocks.VOID_PORTAL_FRAME);
        registerBlockItem("corrupted_grass", ModBlocks.CORRUPTED_GRASS);
        registerBlockItem("corrupted_dirt", ModBlocks.CORRUPTED_DIRT);
        registerBlockItem("corrupted_stone", ModBlocks.CORRUPTED_STONE);
        registerBlockItem("rotted_wood", ModBlocks.ROTTED_WOOD);
        registerBlockItem("corrupted_sand", ModBlocks.CORRUPTED_SAND);
        registerBlockItem("tainted_water", ModBlocks.TAINTED_WATER);
        registerBlockItem("withered_leaves", ModBlocks.WITHERED_LEAVES);
        registerBlockItem("purification_crystal", ModBlocks.PURIFICATION_CRYSTAL);
        
        // Purification system block items
        registerBlockItem("cleansing_altar", ModBlocks.CLEANSING_ALTAR);
        registerBlockItem("sacred_barrier", ModBlocks.SACRED_BARRIER);
        registerBlockItem("sacred_barrier_stone", ModBlocks.SACRED_BARRIER_STONE);
        registerBlockItem("sacred_barrier_brick", ModBlocks.SACRED_BARRIER_BRICK);
        registerBlockItem("sacred_barrier_smooth", ModBlocks.SACRED_BARRIER_SMOOTH);
        
        // Warding torches - special handling for torch items
        Registry.register(Registries.ITEM, 
            Identifier.of(CorruptionMod.MOD_ID, "warding_torch"),
            new VerticallyAttachableBlockItem(
                ModBlocks.WARDING_TORCH, 
                ModBlocks.WARDING_WALL_TORCH, 
                new Item.Settings(), 
                Direction.DOWN
            )
        );
        
        // Register purification potions
        PURIFICATION_POTION_WEAK = Registry.register(
            Registries.ITEM,
            Identifier.of(CorruptionMod.MOD_ID, "purification_potion_weak"),
            new PurificationPotionItem(new Item.Settings().maxCount(16), 1)
        );
        
        PURIFICATION_POTION = Registry.register(
            Registries.ITEM,
            Identifier.of(CorruptionMod.MOD_ID, "purification_potion"),
            new PurificationPotionItem(new Item.Settings().maxCount(16), 2)
        );
        
        PURIFICATION_POTION_STRONG = Registry.register(
            Registries.ITEM,
            Identifier.of(CorruptionMod.MOD_ID, "purification_potion_strong"),
            new PurificationPotionItem(new Item.Settings().maxCount(16), 3)
        );
        
        // Register Void Key
        VOID_KEY = Registry.register(Registries.ITEM, 
            Identifier.of(CorruptionMod.MOD_ID, "void_key"),
            new VoidKeyItem(new Item.Settings().maxDamage(64))
        );
        
        // Register Harbinger boss items
        HARBINGER_CORE = Registry.register(
            Registries.ITEM,
            Identifier.of(CorruptionMod.MOD_ID, "harbinger_core"),
            new HarbingerCoreItem(new Item.Settings().maxCount(16))
        );
        
        ENTROPY_ESSENCE = Registry.register(
            Registries.ITEM,
            Identifier.of(CorruptionMod.MOD_ID, "entropy_essence"),
            new EntropyEssenceItem(new Item.Settings().maxCount(64))
        );
        
        CORRUPTION_CRYSTAL = Registry.register(
            Registries.ITEM,
            Identifier.of(CorruptionMod.MOD_ID, "corruption_crystal"),
            new CorruptionCrystalItem(new Item.Settings().maxCount(64))
        );
        
        ENTROPY_BLADE = Registry.register(
            Registries.ITEM,
            Identifier.of(CorruptionMod.MOD_ID, "entropy_blade"),
            new EntropyBladeItem(new Item.Settings().maxCount(1))
        );
        
        HARBINGER_HELMET = Registry.register(
            Registries.ITEM,
            Identifier.of(CorruptionMod.MOD_ID, "harbinger_helmet"),
            new HarbingerArmorItem(ArmorItem.Type.HELMET, new Item.Settings().maxCount(1))
        );
        
        HARBINGER_CHESTPLATE = Registry.register(
            Registries.ITEM,
            Identifier.of(CorruptionMod.MOD_ID, "harbinger_chestplate"),
            new HarbingerArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Settings().maxCount(1))
        );
        
        HARBINGER_LEGGINGS = Registry.register(
            Registries.ITEM,
            Identifier.of(CorruptionMod.MOD_ID, "harbinger_leggings"),
            new HarbingerArmorItem(ArmorItem.Type.LEGGINGS, new Item.Settings().maxCount(1))
        );
        
        HARBINGER_BOOTS = Registry.register(
            Registries.ITEM,
            Identifier.of(CorruptionMod.MOD_ID, "harbinger_boots"),
            new HarbingerArmorItem(ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1))
        );
        
        HARBINGER_TROPHY = Registry.register(
            Registries.ITEM,
            Identifier.of(CorruptionMod.MOD_ID, "harbinger_trophy"),
            new HarbingerTrophyItem(new Item.Settings().maxCount(1))
        );
        
        // Register spawn egg
        HARBINGER_SPAWN_EGG = Registry.register(
            Registries.ITEM,
            Identifier.of(CorruptionMod.MOD_ID, "harbinger_spawn_egg"),
            new SpawnEggItem(ModEntities.HARBINGER, 0x5A0A5A, 0x8B008B, new Item.Settings())
        );
        
        CorruptionMod.LOGGER.info("Registering mod items");
    }
    
    private static void registerBlockItem(String name, net.minecraft.block.Block block) {
        Registry.register(Registries.ITEM, 
            Identifier.of(CorruptionMod.MOD_ID, name), 
            new BlockItem(block, new Item.Settings())
        );
    }
}
