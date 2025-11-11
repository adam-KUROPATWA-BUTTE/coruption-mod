package com.corruptionmod.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

/**
 * Harbinger Armor Set - Powerful armor crafted from Harbinger drops
 * Each piece provides special effects
 */
public class HarbingerArmorItem extends ArmorItem {
    
    private static final ArmorMaterial HARBINGER_MATERIAL = new ArmorMaterial() {
        @Override
        public int getDurability(Type type) {
            return switch (type) {
                case BOOTS -> 650;
                case LEGGINGS -> 750;
                case CHESTPLATE -> 800;
                case HELMET -> 600;
                default -> 0;
            };
        }
        
        @Override
        public int getProtection(Type type) {
            return switch (type) {
                case BOOTS -> 3;
                case LEGGINGS -> 6;
                case CHESTPLATE -> 8;
                case HELMET -> 3;
                default -> 0;
            };
        }
        
        @Override
        public int getEnchantability() {
            return 20;
        }
        
        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
        }
        
        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }
        
        @Override
        public String getName() {
            return "harbinger";
        }
        
        @Override
        public float getToughness() {
            return 3.0f;
        }
        
        @Override
        public float getKnockbackResistance() {
            return 0.1f;
        }
    };
    
    public HarbingerArmorItem(Type type, Settings settings) {
        super(HARBINGER_MATERIAL, type, settings);
    }
    
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient() && entity instanceof PlayerEntity player) {
            // Apply effects based on armor piece
            if (player.getEquippedStack(getSlotType()).getItem() == this) {
                applyArmorEffect(player, getSlotType());
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
    
    private void applyArmorEffect(PlayerEntity player, Type type) {
        // Only apply every 4 seconds to prevent spam
        if (player.age % 80 != 0) {
            return;
        }
        
        switch (type) {
            case HELMET -> {
                // Corruption resistance (not implemented as status effect, conceptual)
                // Could reduce corruption damage or provide night vision
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 240, 0, false, false));
            }
            case CHESTPLATE -> {
                // Health boost
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 240, 0, false, false));
            }
            case LEGGINGS -> {
                // Speed boost
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 240, 0, false, false));
            }
            case BOOTS -> {
                // Jump boost
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 240, 0, false, false));
            }
        }
    }
    
    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        Type armorType = getSlotType();
        String effectKey = switch (armorType) {
            case HELMET -> "corruption_resistance";
            case CHESTPLATE -> "health_boost";
            case LEGGINGS -> "speed_boost";
            case BOOTS -> "jump_boost";
            default -> "unknown";
        };
        
        tooltip.add(Text.translatable("item.corruptionmod.harbinger_armor.tooltip")
            .formatted(Formatting.DARK_PURPLE));
        tooltip.add(Text.translatable("item.corruptionmod.harbinger_armor." + effectKey)
            .formatted(Formatting.GRAY));
    }
    
    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
