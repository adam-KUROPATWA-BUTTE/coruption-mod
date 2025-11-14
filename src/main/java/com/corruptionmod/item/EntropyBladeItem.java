package com.corruptionmod.item;

import com.corruptionmod.CorruptionMod;
import com.corruptionmod.ModEffects;
import com.corruptionmod.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

/**
 * Entropy Blade - Powerful sword crafted from Harbinger drops
 * Applies corruption effect on hit
 */
public class EntropyBladeItem extends SwordItem {
    
    private static final RegistryEntry<ToolMaterial> ENTROPY_MATERIAL = registerToolMaterial();

    private static RegistryEntry<ToolMaterial> registerToolMaterial() {
        return Registry.registerReference(Registries.TOOL_MATERIAL,
            Identifier.of(CorruptionMod.MOD_ID, "entropy"),
            new ToolMaterial(
                BlockTags.INCORRECT_FOR_NETHERITE_TOOL, // inverseTag
                2031, // durability
                9.0F, // miningSpeed
                4.0F, // attackDamage
                15, // enchantability
                () -> Ingredient.ofItems(ModItems.ENTROPY_ESSENCE)
            )
        );
    }

    public EntropyBladeItem(Settings settings) {
        super(ENTROPY_MATERIAL, new Item.Settings().attributeModifiers(
            SwordItem.createAttributeModifiers(ENTROPY_MATERIAL, 12, -2.4f)
        ));
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
