package com.corruptionmod.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

/**
 * Harbinger's Core - Main boss drop used for crafting
 */
public class HarbingerCoreItem extends Item {
    
    public HarbingerCoreItem(Settings settings) {
        super(settings);
    }
    
    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.corruptionmod.harbinger_core.tooltip")
            .formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
    }
    
    @Override
    public boolean hasGlint(ItemStack stack) {
        return true; // Always enchanted glow
    }
}
