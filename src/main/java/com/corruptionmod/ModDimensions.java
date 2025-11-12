package com.corruptionmod;

import com.corruptionmod.world.ModBiomes;
import com.corruptionmod.world.VoidRealmBiomeSource;
import com.corruptionmod.world.VoidRealmChunkGenerator;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

/**
 * Gère l'enregistrement de la dimension du Néant (Void Realm).
 */
public class ModDimensions {
    public static final Identifier LE_NEANT_ID = Identifier.of(CorruptionMod.MOD_ID, "le_neant");
    public static final RegistryKey<World> LE_NEANT_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, LE_NEANT_ID);
    public static final RegistryKey<DimensionOptions> LE_NEANT_DIMENSION_KEY = RegistryKey.of(RegistryKeys.DIMENSION, LE_NEANT_ID);

    public static void register() {
        CorruptionMod.LOGGER.info("Registering Void Realm dimension");
        
        // Register biomes
        ModBiomes.register();
        
        // Dimension is registered through data packs and JSON files
        // The dimension type is defined in dimension/le_neant.json
        // The chunk generator and biome source are configured at runtime
    }

    /**
     * Gets the Void Realm server world instance.
     */
    public static ServerWorld getVoidRealmWorld(MinecraftServer server) {
        return server.getWorld(LE_NEANT_WORLD_KEY);
    }
}
