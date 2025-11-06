package com.corruptionmod;

import com.corruptionmod.world.ModBiomes;
import com.corruptionmod.world.VoidRealmChunkGenerator;

/**
 * Gère la génération des zones corrompues et structures du Néant.
 */
public class ModWorldGen {
    public static void register() {
        CorruptionMod.LOGGER.info("Registering world generation features");
        
        // Register biomes
        ModBiomes.register();
        
        // World generation features are configured through JSON files
        // Structures, features, and decorations are placed based on biome definitions
    }
}
