package com.corruptionmod.world;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.List;
import java.util.stream.Stream;

/**
 * Custom biome source for the Void Realm dimension.
 * Distributes the four biomes across the dimension.
 */
public class VoidRealmBiomeSource extends BiomeSource {
    public static final MapCodec<VoidRealmBiomeSource> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    RegistryKeys.BIOME.codec().fieldOf("biome_registry").forGetter(source -> source.biomeRegistry)
            ).apply(instance, VoidRealmBiomeSource::new)
    );

    private final Registry<Biome> biomeRegistry;
    private final List<RegistryEntry<Biome>> biomes;

    public VoidRealmBiomeSource(Registry<Biome> biomeRegistry) {
        this.biomeRegistry = biomeRegistry;
        this.biomes = List.of(
                biomeRegistry.getEntry(ModBiomes.BLIGHTED_PLAINS).orElseThrow(),
                biomeRegistry.getEntry(ModBiomes.WRITHING_FOREST).orElseThrow(),
                biomeRegistry.getEntry(ModBiomes.OBSIDIAN_WASTES).orElseThrow(),
                biomeRegistry.getEntry(ModBiomes.FLESH_VALLEYS).orElseThrow()
        );
    }

    @Override
    protected MapCodec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    public Stream<RegistryEntry<Biome>> biomes() {
        return biomes.stream();
    }

    @Override
    public RegistryEntry<Biome> getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise) {
        // Simple distribution based on coordinates
        int biomeX = Math.floorDiv(x, 64);
        int biomeZ = Math.floorDiv(z, 64);
        
        // Use coordinate-based distribution for variety
        int hash = (biomeX * 374761393 + biomeZ * 668265263) & 0x7FFFFFFF;
        int biomeIndex = hash % biomes.size();
        
        return biomes.get(biomeIndex);
    }
}
