package com.corruptionmod.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

/**
 * Harbinger Banner - Trophy item from defeating the boss
 */
public class HarbingerBannerItem extends BlockItem {
    
    public HarbingerBannerItem(Block block, Settings settings) {
        super(block, settings);
    }
    
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.corruptionmod.harbinger_banner.tooltip")
            .formatted(Formatting.GOLD, Formatting.ITALIC));
    }
}
