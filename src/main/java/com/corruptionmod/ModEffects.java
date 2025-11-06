package com.corruptionmod;

import com.corruptionmod.effect.CorruptionEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Registers all mod status effects.
 */
public class ModEffects {
    public static StatusEffect PURIFICATION;
    
    public static void register() {
        PURIFICATION = Registry.register(
            Registry.STATUS_EFFECT,
            new Identifier(CorruptionMod.MOD_ID, "purification"),
            new PurificationEffect()
 * Registers status effects for the mod
 */
public class ModEffects {
    public static StatusEffect CORRUPTION;

    public static void register() {
        CORRUPTION = Registry.register(
            Registry.STATUS_EFFECT,
            new Identifier(CorruptionMod.MOD_ID, "corruption"),
            new CorruptionEffect()
        );
    }
}
