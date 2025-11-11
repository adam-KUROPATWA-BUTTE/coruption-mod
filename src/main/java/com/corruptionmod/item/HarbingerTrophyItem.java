package com.corruptionmod.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;

import java.util.List;

/**
 * Harbinger Trophy - Rare decorative item from defeating the boss
 */
public class HarbingerTrophyItem extends Item {
    
    public HarbingerTrophyItem(Settings settings) {
        super(settings.rarity(Rarity.EPIC));
    }
    
    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.corruptionmod.harbinger_trophy.tooltip")
            .formatted(Formatting.GOLD, Formatting.BOLD));
        tooltip.add(Text.translatable("item.corruptionmod.harbinger_trophy.subtitle")
            .formatted(Formatting.GRAY, Formatting.ITALIC));
    }
    
    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
