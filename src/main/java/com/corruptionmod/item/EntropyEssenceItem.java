package com.corruptionmod.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

/**
 * Entropy Essence - Boss crafting material
 */
public class EntropyEssenceItem extends Item {
    
    public EntropyEssenceItem(Settings settings) {
        super(settings);
    }
    
    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.corruptionmod.entropy_essence.tooltip")
            .formatted(Formatting.DARK_PURPLE));
    }
    
    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
