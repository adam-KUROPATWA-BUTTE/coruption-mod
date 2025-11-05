package com.corruptionmod.event;

import com.corruptionmod.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Gère la propagation de la corruption.
 * Version prototype : effectue une propagation par monde à intervalles aléatoires (20-40s)
 * et échantillonne des positions dans les chunks chargés pour étendre la corruption.
 */
public class WorldCorruptionTicker {
    private static final long MIN_TICKS = 20L * 20L; // 20s
    private static final long MAX_TICKS = 20L * 40L; // 40s
    private static final Random RANDOM = new Random();

    // Prochaine tick de propagation par dimension
    private static final Map<World, Long> nextSpreadTick = new HashMap<>();

    /**
     * Méthode appelée depuis CorruptionMod (ServerTickEvents.END_WORLD_TICK)
     */
    public static void tick(ServerWorld world) {
        if (world == null || world.isClient) return;

        long time = world.getTime();
        long next = nextSpreadTick.computeIfAbsent(world, w -> time + randomInterval());

        if (time < next) return;

        // Exécute une passe de propagation
        spreadCorruption(world);

        // Planifie la prochaine propagation
        nextSpreadTick.put(world, time + randomInterval());
    }

    private static long randomInterval() {
        return MIN_TICKS + (long)(RANDOM.nextDouble() * (MAX_TICKS - MIN_TICKS));
    }

    /**
     * Logique prototype de propagation : pour chaque chunk chargé, on échantillonne
     * quelques positions et si un bloc corrompu est trouvé, on tente de corrompre
     * un voisin selon des priorités simples.
     */
    private static void spreadCorruption(ServerWorld world) {
        // Prototype : échantillonne autour des joueurs connectés (chunks chargés), réduit la portée CPU
        final int SAMPLES_PER_PLAYER = 8;
        final int SAMPLE_RADIUS = 64;

        for (net.minecraft.entity.player.PlayerEntity player : world.getPlayers()) {
            int px = player.getBlockX();
            int pz = player.getBlockZ();

            for (int s = 0; s < SAMPLES_PER_PLAYER; s++) {
                int x = px + RANDOM.nextInt(SAMPLE_RADIUS * 2 + 1) - SAMPLE_RADIUS;
                int z = pz + RANDOM.nextInt(SAMPLE_RADIUS * 2 + 1) - SAMPLE_RADIUS;
                int y = world.getTopY() - 1;

                BlockPos pos = new BlockPos(x, y, z);
                // Descend jusqu'à trouver un bloc solide
                while (pos.getY() > world.getBottomY() && world.isAir(pos)) {
                    pos = pos.down();
                }

                BlockState state = world.getBlockState(pos);
                // Si c'est un bloc de corruption source, tente d'étendre
                if (isCorruptionSource(state)) {
                    attemptSpreadFrom(world, pos);
                }
            }
        }
    }

    /**
     * Force une passe de propagation immédiate dans ce monde (utilisé par la commande)
     */
    public static void forceSpread(ServerWorld world) {
        if (world == null || world.isClient) return;
        spreadCorruption(world);
        // Réduire le délai avant la prochaine passe
        nextSpreadTick.put(world, world.getTime() + MIN_TICKS);
    }

    private static boolean isCorruptionSource(BlockState state) {
        // Any block class that extends CorruptionBlock is considered a source.
        return state.getBlock() instanceof com.corruptionmod.block.CorruptionBlock;
    }

    private static void attemptSpreadFrom(ServerWorld world, BlockPos pos) {
        // Directions cardinales + up/down
        BlockPos[] neighbors = new BlockPos[] {
            pos.north(), pos.south(), pos.east(), pos.west(), pos.up(), pos.down()
        };

        // Priorité simple : grass/dirt > stone > wood > sand > other
        // On cherchera un voisin convertible et on appliquera une probabilité selon le type
        for (BlockPos npos : neighbors) {
            BlockState targetState = world.getBlockState(npos);
            if (canBeCorrupted(targetState)) {
                float chance = corruptionChanceFor(targetState);
                // Si la cible est de l'eau, ralentir la propagation de 80%
                if (targetState.getMaterial() == net.minecraft.block.Material.WATER) chance *= 0.2f;
                if (RANDOM.nextFloat() < chance) {
                    // Choisir la variante corrompue appropriée
                    BlockState newState = toCorruptedVariant(targetState);
                    world.setBlockState(npos, newState);
                    // Particules et son pour l'effet
                    world.syncWorldEvent(2001, npos, Block.getRawIdFromState(targetState)); // effet de casse
                    break; // un seul voisin infecté par source durant cette passe
                }
            }
        }
    }

    private static BlockState toCorruptedVariant(BlockState original) {
        String key = original.getBlock().getTranslationKey().toLowerCase();
        if (key.contains("grass")) return ModBlocks.CORRUPTED_GRASS.getDefaultState();
        if (key.contains("dirt")) return ModBlocks.CORRUPTED_DIRT.getDefaultState();
        if (key.contains("stone")) return ModBlocks.CORRUPTED_STONE.getDefaultState();
        if (key.contains("log") || key.contains("wood")) return ModBlocks.ROTTED_WOOD.getDefaultState();
        if (key.contains("sand")) return ModBlocks.CORRUPTED_SAND.getDefaultState();
        if (key.contains("water")) return ModBlocks.TAINTED_WATER.getDefaultState();
        if (key.contains("leaves")) return ModBlocks.WITHERED_LEAVES.getDefaultState();
        return ModBlocks.CORRUPTION_BLOCK.getDefaultState();
    }

    private static boolean canBeCorrupted(BlockState state) {
        Block block = state.getBlock();
        // Empêcher la corruption à travers obsidian ou bedrock
        String name = block.getTranslationKey().toLowerCase();
        if (name.contains("obsidian") || name.contains("bedrock")) return false;
        // Par défaut, on peut corrompre les blocs naturels
        return true;
    }

    private static float corruptionChanceFor(BlockState state) {
        String key = state.getBlock().getTranslationKey().toLowerCase();
        if (key.contains("grass") || key.contains("dirt")) return 0.6f;
        if (key.contains("stone")) return 0.35f;
        if (key.contains("log") || key.contains("wood")) return 0.25f;
        if (key.contains("sand")) return 0.2f;
        if (key.contains("leaves")) return 0.3f;
        // Autres
        return 0.05f;
    }
}
