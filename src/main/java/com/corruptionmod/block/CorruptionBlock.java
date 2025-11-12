package com.corruptionmod.block;

import com.corruptionmod.ModBlocks;
import com.corruptionmod.util.CorruptionUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Bloc source de corruption : tente de propager vers les voisins en choisissant
 * la variante corrompue appropriée selon le type de bloc cible.
 */
public class CorruptionBlock extends Block {
    public CorruptionBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // 15% de chance d'essayer de propager
        if (random.nextFloat() >= 0.15f) return;

        // Collecte des voisins convertibles
        Direction[] dirs = new Direction[] {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.UP, Direction.DOWN};

        // Priorité : grass/dirt > stone > wood > sand > other
        Direction chosen = null;
        float chosenChance = 0f;
        BlockState chosenState = null;

        for (Direction d : dirs) {
            BlockPos tpos = pos.offset(d);
            
            // Check if target position is in a purified zone
            if (com.corruptionmod.event.PurificationManager.isInPurifiedZone(world, tpos)) {
                continue; // Skip positions protected by purification crystals
            }
            
            BlockState tstate = world.getBlockState(tpos);
            if (!canCorrupt(tstate)) continue;

            float chance = corruptionChanceFor(tstate);
            // Si eau, ralentir la propagation de 80%
            String key = tstate.getBlock().getTranslationKey().toLowerCase();
            if (key.contains("water")) chance *= 0.2f;

            // Préférer la cible avec la plus haute probabilité (simple heuristique de priorité)
            if (chance > chosenChance) {
                chosen = d;
                chosenChance = chance;
                chosenState = tstate;
            }
        }

        if (chosen != null && chosenState != null && random.nextFloat() < chosenChance) {
            BlockPos targetPos = pos.offset(chosen);
            BlockState newState = getCorruptedVariant(chosenState);
            world.setBlockState(targetPos, newState);
            spawnCorruptionParticles(world, targetPos);
            world.syncWorldEvent(2001, targetPos, Block.getRawIdFromState(chosenState));
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(5) == 0) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + random.nextDouble();
            double z = pos.getZ() + random.nextDouble();
            world.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.02, 0.0);
        }
    }

    private boolean canCorrupt(BlockState state) {
        Block block = state.getBlock();
        
        // Cannot corrupt barrier blocks
        if (block == net.minecraft.block.Blocks.OBSIDIAN || 
            block == net.minecraft.block.Blocks.CRYING_OBSIDIAN ||
            block == net.minecraft.block.Blocks.BEDROCK ||
            block == ModBlocks.PURIFICATION_CRYSTAL) {
            return false;
        }
        
        // Cannot corrupt already corrupted blocks
        if (block instanceof CorruptionBlock) {
            return false;
        }
        
        // Check block name for additional barriers
        String key = state.getBlock().getTranslationKey().toLowerCase();
        if (key.contains("obsidian") || key.contains("bedrock") || key.contains("purified")) return false;
        
        // Only allow corruption of natural blocks
        return CorruptionUtil.isNaturalBlock(state);
    }

    private BlockState getCorruptedVariant(BlockState originalState) {
        String key = originalState.getBlock().getTranslationKey().toLowerCase();
        if (key.contains("grass")) return ModBlocks.CORRUPTED_GRASS.getDefaultState();
        if (key.contains("dirt")) return ModBlocks.CORRUPTED_DIRT.getDefaultState();
        if (key.contains("stone")) return ModBlocks.CORRUPTED_STONE.getDefaultState();
        if (key.contains("log") || key.contains("wood")) return ModBlocks.ROTTED_WOOD.getDefaultState();
        if (key.contains("sand")) return ModBlocks.CORRUPTED_SAND.getDefaultState();
        if (key.contains("gravel")) return ModBlocks.CORRUPTED_STONE.getDefaultState(); // Gravel becomes corrupted stone
        if (key.contains("water")) return ModBlocks.TAINTED_WATER.getDefaultState();
        if (key.contains("leaves")) return ModBlocks.WITHERED_LEAVES.getDefaultState();
        // Fallback
        return ModBlocks.CORRUPTION_BLOCK.getDefaultState();
    }
    
    private float corruptionChanceFor(BlockState state) {
        String key = state.getBlock().getTranslationKey().toLowerCase();
        if (key.contains("grass") || key.contains("dirt")) return 0.6f;
        if (key.contains("stone")) return 0.35f;
        if (key.contains("log") || key.contains("wood")) return 0.25f;
        if (key.contains("sand")) return 0.2f;
        if (key.contains("gravel")) return 0.3f;
        if (key.contains("leaves")) return 0.3f;
        // Other natural blocks
        return 0.05f;
    }

    private void spawnCorruptionParticles(World world, BlockPos pos) {
        Random random = world.getRandom();
        for (int i = 0; i < 6; i++) {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.8;
            double y = pos.getY() + 0.5 + random.nextDouble() * 0.5;
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.8;
            world.addParticle(ParticleTypes.PORTAL, x, y, z, 0.0, 0.07, 0.0);
        }
    }
}