package com.corruptionmod.world;

import com.corruptionmod.ModDimensions;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

/**
 * Handles ambient particle effects in the Void Realm dimension.
 */
public class VoidRealmAmbientEffects {
    private static final Random RANDOM = new Random();

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            if (world.getRegistryKey() == ModDimensions.LE_NEANT_WORLD_KEY) {
                tickAmbientEffects(world);
            }
        });
    }

    private static void tickAmbientEffects(ServerWorld world) {
        // Spawn ambient particles every few ticks
        if (world.getTime() % 20 != 0) {
            return;
        }

        // Spawn particles near players
        world.getPlayers().forEach(player -> {
            BlockPos pos = player.getBlockPos();
            
            // Spawn void particles around player
            for (int i = 0; i < 3; i++) {
                double x = pos.getX() + (RANDOM.nextDouble() - 0.5) * 32;
                double y = pos.getY() + RANDOM.nextDouble() * 16;
                double z = pos.getZ() + (RANDOM.nextDouble() - 0.5) * 32;
                
                world.spawnParticles(
                    ParticleTypes.PORTAL,
                    x, y, z,
                    1,
                    0, 0.1, 0,
                    0.01
                );
            }
            
            // Occasional smoke particles
            if (RANDOM.nextInt(10) == 0) {
                double x = pos.getX() + (RANDOM.nextDouble() - 0.5) * 16;
                double y = pos.getY() + RANDOM.nextDouble() * 8;
                double z = pos.getZ() + (RANDOM.nextDouble() - 0.5) * 16;
                
                world.spawnParticles(
                    ParticleTypes.SMOKE,
                    x, y, z,
                    1,
                    0, 0.05, 0,
                    0.01
                );
            }
        });
    }
}
