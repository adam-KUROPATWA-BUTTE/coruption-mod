package com.corruptionmod.world;

import com.corruptionmod.CorruptionMod;
import net.minecraft.util.Identifier;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;

/**
 * Registry keys for all Void Realm biomes.
 */
public class ModBiomes {
    public static final RegistryKey<Biome> BLIGHTED_PLAINS = registerBiome("blighted_plains");
    public static final RegistryKey<Biome> WRITHING_FOREST = registerBiome("writhing_forest");
    public static final RegistryKey<Biome> OBSIDIAN_WASTES = registerBiome("obsidian_wastes");
    public static final RegistryKey<Biome> FLESH_VALLEYS = registerBiome("flesh_valleys");

    private static RegistryKey<Biome> registerBiome(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, Identifier.of(CorruptionMod.MOD_ID, name));
    }

    public static void register() {
        // Biomes are registered through JSON files in resources
        CorruptionMod.LOGGER.info("Registering Void Realm biomes");
    }
}
