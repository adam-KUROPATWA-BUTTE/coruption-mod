package com.corruptionmod.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

/**
 * Corruption Crystal - Boss crafting material
 */
public class CorruptionCrystalItem extends Item {
    
    public CorruptionCrystalItem(Settings settings) {
        super(settings);
    }
    
    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.corruptionmod.corruption_crystal.tooltip")
            .formatted(Formatting.DARK_RED));
    }
}
