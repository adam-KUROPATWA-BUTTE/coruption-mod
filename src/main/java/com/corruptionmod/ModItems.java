package com.corruptionmod;

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
