package com.corruptionmod;

import com.corruptionmod.effect.CorruptionEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

/**
 * Registers status effects for the mod
 */
public class ModEffects {
    public static StatusEffect CORRUPTION;

    public static void register() {
        CORRUPTION = Registry.register(
            Registries.STATUS_EFFECT,
            Identifier.of(CorruptionMod.MOD_ID, "corruption"),
            new CorruptionEffect()
        );
    }
}
