package com.corruptionmod.world;

import com.corruptionmod.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.Blender;
import net.minecraft.world.gen.noise.NoiseConfig;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Custom chunk generator for the Void Realm dimension.
 * Generates terrain appropriate for each biome.
 */
public class VoidRealmChunkGenerator extends ChunkGenerator {
    public static final Codec<VoidRealmChunkGenerator> CODEC = BiomeSource.CODEC.fieldOf("biome_source")
            .xmap(VoidRealmChunkGenerator::new, ChunkGenerator::getBiomeSource).codec();

    public VoidRealmChunkGenerator(BiomeSource biomeSource) {
        super(biomeSource);
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public void carve(ChunkRegion chunkRegion, long seed, NoiseConfig noiseConfig, BiomeAccess biomeAccess, 
                      StructureAccessor structureAccessor, Chunk chunk, GenerationStep.Carver carverStep) {
        // No carving for now
    }

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) {
        BlockState voidStone = ModBlocks.VOID_STONE.getDefaultState();
        BlockState blightedGrass = ModBlocks.BLIGHTED_GRASS.getDefaultState();
        BlockState corruptionFlesh = ModBlocks.CORRUPTION_FLESH.getDefaultState();
        
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = chunk.getPos().getStartX() + x;
                int worldZ = chunk.getPos().getStartZ() + z;
                
                // Get biome at this position
                var biome = chunk.getBiomeForNoiseGen(x >> 2, 64 >> 2, z >> 2);
                
                // Generate basic terrain layers
                int baseHeight = 64 + (int) (Math.sin(worldX * 0.1) * 3 + Math.cos(worldZ * 0.1) * 3);
                
                for (int y = region.getBottomY(); y < baseHeight; y++) {
                    mutable.set(worldX, y, worldZ);
                    if (y < baseHeight - 4) {
                        chunk.setBlockState(mutable, voidStone, false);
                    } else if (y < baseHeight - 1) {
                        chunk.setBlockState(mutable, voidStone, false);
                    } else if (y < baseHeight) {
                        // Top layer varies by biome
                        if (biome.matchesKey(ModBiomes.FLESH_VALLEYS)) {
                            chunk.setBlockState(mutable, corruptionFlesh, false);
                        } else {
                            chunk.setBlockState(mutable, blightedGrass, false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, NoiseConfig noiseConfig,
                                                    StructureAccessor structureAccessor, Chunk chunk) {
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinimumY() {
        return 0;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world, NoiseConfig noiseConfig) {
        return 64;
    }

    @Override
    public net.minecraft.world.gen.chunk.VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world, NoiseConfig noiseConfig) {
        return new net.minecraft.world.gen.chunk.VerticalBlockSample(
                world.getBottomY(),
                new BlockState[]{ModBlocks.VOID_STONE.getDefaultState()}
        );
    }

    @Override
    public void getDebugHudText(List<String> text, NoiseConfig noiseConfig, BlockPos pos) {
        text.add("Void Realm Generator");
    }
}
