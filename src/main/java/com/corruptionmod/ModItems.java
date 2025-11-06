package com.corruptionmod;

import com.corruptionmod.potion.PurificationPotionItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

/**
 * Registers all mod items including block items and potions.
 */
public class ModItems {
    // Purification potions
    public static Item PURIFICATION_POTION_WEAK;
    public static Item PURIFICATION_POTION;
    public static Item PURIFICATION_POTION_STRONG;
    
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
        Registry.register(Registry.ITEM, 
            new Identifier(CorruptionMod.MOD_ID, "warding_torch"),
            new VerticallyAttachableBlockItem(
                ModBlocks.WARDING_TORCH, 
                ModBlocks.WARDING_WALL_TORCH, 
                new Item.Settings(), 
                Direction.DOWN
            )
        );
        
        // Register purification potions
        PURIFICATION_POTION_WEAK = Registry.register(
            Registry.ITEM,
            new Identifier(CorruptionMod.MOD_ID, "purification_potion_weak"),
            new PurificationPotionItem(new Item.Settings().maxCount(16), 1)
        );
        
        PURIFICATION_POTION = Registry.register(
            Registry.ITEM,
            new Identifier(CorruptionMod.MOD_ID, "purification_potion"),
            new PurificationPotionItem(new Item.Settings().maxCount(16), 2)
        );
        
        PURIFICATION_POTION_STRONG = Registry.register(
            Registry.ITEM,
            new Identifier(CorruptionMod.MOD_ID, "purification_potion_strong"),
            new PurificationPotionItem(new Item.Settings().maxCount(16), 3)
        );
    }
    
    private static void registerBlockItem(String name, net.minecraft.block.Block block) {
        Registry.register(Registry.ITEM, 
            new Identifier(CorruptionMod.MOD_ID, name), 
            new BlockItem(block, new Item.Settings())
        );
import com.corruptionmod.item.VoidKeyItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Registers all items for the mod.
 */
public class ModItems {
    public static Item VOID_KEY;

    public static void register() {
        VOID_KEY = Registry.register(Registry.ITEM, 
            new Identifier(CorruptionMod.MOD_ID, "void_key"),
            new VoidKeyItem(new Item.Settings().maxDamage(64)));
        
        CorruptionMod.LOGGER.info("Registering mod items");
    }
}
