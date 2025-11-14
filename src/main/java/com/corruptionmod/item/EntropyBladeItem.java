package com.corruptionmod.item;

import com.corruptionmod.CorruptionMod;
import com.corruptionmod.ModEffects;
import com.corruptionmod.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

/**
 * Entropy Blade - Powerful sword crafted from Harbinger drops
 * Applies corruption effect on hit
 */
public class EntropyBladeItem extends SwordItem {
    
    private static final ToolMaterial ENTROPY_MATERIAL = new ToolMaterial() {
        @Override
        public int getDurability() {
            return 2031;
        }

        @Override
        public float getMiningSpeedMultiplier() {
            return 9.0F;
        }

        @Override
        public float getAttackDamage() {
            return 4.0F;
        }

        @Override
        public TagKey<Block> getInverseTag() {
            return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
        }

        @Override
        public int getEnchantability() {
            return 15;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(ModItems.ENTROPY_ESSENCE);
        }
    };

    public EntropyBladeItem(Settings settings) {
        super(ENTROPY_MATERIAL, 12, -2.4f, settings);
    }
    
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Apply corruption effect on hit
        target.addStatusEffect(new StatusEffectInstance(ModEffects.CORRUPTION, 100, 1));
        return super.postHit(stack, target, attacker);
    }
    
    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.corruptionmod.entropy_blade.tooltip")
            .formatted(Formatting.DARK_PURPLE));
        tooltip.add(Text.translatable("item.corruptionmod.entropy_blade.effect")
            .formatted(Formatting.GRAY));
    }
    
    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
