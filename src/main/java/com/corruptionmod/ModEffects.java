package com.corruptionmod;

import com.corruptionmod.effect.CorruptionEffect;
import com.corruptionmod.effect.PurificationEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

/**
 * Registers status effects for the mod
 */
public class ModEffects {
    public static RegistryEntry<StatusEffect> CORRUPTION;
    public static RegistryEntry<StatusEffect> PURIFICATION;

    public static void register() {
        CORRUPTION = Registry.registerReference(
            Registries.STATUS_EFFECT,
            Identifier.of(CorruptionMod.MOD_ID, "corruption"),
            new CorruptionEffect()
        );
        
        PURIFICATION = Registry.registerReference(
            Registries.STATUS_EFFECT,
            Identifier.of(CorruptionMod.MOD_ID, "purification"),
            new PurificationEffect()
        );
    }
}
