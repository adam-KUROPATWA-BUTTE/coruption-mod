package com.corruptionmod.client.particle;

import com.corruptionmod.config.CorruptionConfig;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

/**
 * Helper class for spawning custom particle effects throughout the mod.
 * Respects particle density configuration for performance.
 */
public class ParticleHelper {
    
    private static final Random RANDOM = new Random();
    
    /**
     * Spawn corruption spread particles when a block becomes corrupted
     */
    public static void spawnCorruptionSpreadParticles(ClientWorld world, BlockPos pos) {
        if (!shouldSpawnParticles()) return;
        
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;
        
        int count = (int)(8 * CorruptionConfig.particleDensityMultiplier);
        
        for (int i = 0; i < count; i++) {
            double offsetX = (RANDOM.nextDouble() - 0.5) * 0.5;
            double offsetY = (RANDOM.nextDouble() - 0.5) * 0.5;
            double offsetZ = (RANDOM.nextDouble() - 0.5) * 0.5;
            
            double velocityX = (RANDOM.nextDouble() - 0.5) * 0.1;
            double velocityY = RANDOM.nextDouble() * 0.1;
            double velocityZ = (RANDOM.nextDouble() - 0.5) * 0.1;
            
            world.addParticle(ParticleTypes.PORTAL,
                x + offsetX, y + offsetY, z + offsetZ,
                velocityX, velocityY, velocityZ);
        }
        
        // Add smoke particles for extra effect
        for (int i = 0; i < count / 2; i++) {
            double offsetX = (RANDOM.nextDouble() - 0.5) * 0.5;
            double offsetY = (RANDOM.nextDouble() - 0.5) * 0.5;
            double offsetZ = (RANDOM.nextDouble() - 0.5) * 0.5;
            
            world.addParticle(ParticleTypes.SMOKE,
                x + offsetX, y + offsetY, z + offsetZ,
                0, 0.05, 0);
        }
    }
    
    /**
     * Spawn purification particles when corruption is cleansed
     */
    public static void spawnPurificationParticles(ClientWorld world, BlockPos pos) {
        if (!shouldSpawnParticles()) return;
        
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;
        
        int count = (int)(12 * CorruptionConfig.particleDensityMultiplier);
        
        // White sparkles
        for (int i = 0; i < count; i++) {
            double offsetX = (RANDOM.nextDouble() - 0.5) * 0.8;
            double offsetY = (RANDOM.nextDouble() - 0.5) * 0.8;
            double offsetZ = (RANDOM.nextDouble() - 0.5) * 0.8;
            
            double velocityX = (RANDOM.nextDouble() - 0.5) * 0.15;
            double velocityY = RANDOM.nextDouble() * 0.2;
            double velocityZ = (RANDOM.nextDouble() - 0.5) * 0.15;
            
            world.addParticle(ParticleTypes.END_ROD,
                x + offsetX, y + offsetY, z + offsetZ,
                velocityX, velocityY, velocityZ);
        }
        
        // Glowing particles
        for (int i = 0; i < count / 2; i++) {
            double offsetX = (RANDOM.nextDouble() - 0.5) * 0.6;
            double offsetY = (RANDOM.nextDouble() - 0.5) * 0.6;
            double offsetZ = (RANDOM.nextDouble() - 0.5) * 0.6;
            
            world.addParticle(ParticleTypes.ELECTRIC_SPARK,
                x + offsetX, y + offsetY, z + offsetZ,
                0, 0.1, 0);
        }
    }
    
    /**
     * Spawn ambient corruption particles around heavily corrupted areas
     */
    public static void spawnAmbientCorruptionParticles(ClientWorld world, BlockPos pos, float corruptionLevel) {
        if (!shouldSpawnParticles()) return;
        if (RANDOM.nextFloat() > corruptionLevel * 0.1) return; // Spawn less frequently in low corruption
        
        double x = pos.getX() + RANDOM.nextDouble();
        double y = pos.getY() + RANDOM.nextDouble();
        double z = pos.getZ() + RANDOM.nextDouble();
        
        // Floating spores
        if (RANDOM.nextFloat() < 0.3 * CorruptionConfig.particleDensityMultiplier) {
            world.addParticle(ParticleTypes.MYCELIUM,
                x, y, z,
                0, 0.02, 0);
        }
        
        // Smoke wisps
        if (RANDOM.nextFloat() < 0.2 * CorruptionConfig.particleDensityMultiplier) {
            world.addParticle(ParticleTypes.SMOKE,
                x, y, z,
                (RANDOM.nextDouble() - 0.5) * 0.02,
                0.01,
                (RANDOM.nextDouble() - 0.5) * 0.02);
        }
        
        // Purple portal particles in very high corruption
        if (corruptionLevel > 0.7 && RANDOM.nextFloat() < 0.15 * CorruptionConfig.particleDensityMultiplier) {
            world.addParticle(ParticleTypes.PORTAL,
                x, y, z,
                (RANDOM.nextDouble() - 0.5) * 0.1,
                -0.05,
                (RANDOM.nextDouble() - 0.5) * 0.1);
        }
    }
    
    /**
     * Spawn particles for purification crystal active effect
     */
    public static void spawnCrystalGlowParticles(ClientWorld world, BlockPos pos) {
        if (!shouldSpawnParticles()) return;
        if (RANDOM.nextFloat() > 0.2) return; // Don't spawn every frame
        
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;
        
        int count = (int)(2 * CorruptionConfig.particleDensityMultiplier);
        
        for (int i = 0; i < count; i++) {
            double angle = RANDOM.nextDouble() * Math.PI * 2;
            double radius = 0.3 + RANDOM.nextDouble() * 0.2;
            
            double offsetX = Math.cos(angle) * radius;
            double offsetY = (RANDOM.nextDouble() - 0.5) * 0.4;
            double offsetZ = Math.sin(angle) * radius;
            
            world.addParticle(ParticleTypes.END_ROD,
                x + offsetX, y + offsetY, z + offsetZ,
                0, 0.05, 0);
        }
    }
    
    /**
     * Spawn particles for boss attacks
     */
    public static void spawnBossAttackParticles(ClientWorld world, Vec3d position) {
        if (!shouldSpawnParticles()) return;
        
        int count = (int)(15 * CorruptionConfig.particleDensityMultiplier);
        
        for (int i = 0; i < count; i++) {
            double offsetX = (RANDOM.nextDouble() - 0.5) * 2.0;
            double offsetY = (RANDOM.nextDouble() - 0.5) * 2.0;
            double offsetZ = (RANDOM.nextDouble() - 0.5) * 2.0;
            
            double velocityX = (RANDOM.nextDouble() - 0.5) * 0.3;
            double velocityY = (RANDOM.nextDouble() - 0.5) * 0.3;
            double velocityZ = (RANDOM.nextDouble() - 0.5) * 0.3;
            
            world.addParticle(ParticleTypes.SOUL_FIRE_FLAME,
                position.x + offsetX,
                position.y + offsetY,
                position.z + offsetZ,
                velocityX, velocityY, velocityZ);
        }
        
        // Add explosion particles for impact
        for (int i = 0; i < count / 3; i++) {
            world.addParticle(ParticleTypes.LARGE_SMOKE,
                position.x + (RANDOM.nextDouble() - 0.5),
                position.y + (RANDOM.nextDouble() - 0.5),
                position.z + (RANDOM.nextDouble() - 0.5),
                0, 0.1, 0);
        }
    }
    
    /**
     * Spawn particles for corrupted entity death
     */
    public static void spawnCorruptedDeathParticles(ClientWorld world, Vec3d position) {
        if (!shouldSpawnParticles()) return;
        
        int count = (int)(20 * CorruptionConfig.particleDensityMultiplier);
        
        for (int i = 0; i < count; i++) {
            double offsetX = (RANDOM.nextDouble() - 0.5) * 1.0;
            double offsetY = (RANDOM.nextDouble() - 0.5) * 1.0;
            double offsetZ = (RANDOM.nextDouble() - 0.5) * 1.0;
            
            double velocityX = (RANDOM.nextDouble() - 0.5) * 0.2;
            double velocityY = RANDOM.nextDouble() * 0.2;
            double velocityZ = (RANDOM.nextDouble() - 0.5) * 0.2;
            
            world.addParticle(ParticleTypes.PORTAL,
                position.x + offsetX,
                position.y + offsetY,
                position.z + offsetZ,
                velocityX, velocityY, velocityZ);
        }
        
        // Add smoke burst
        for (int i = 0; i < count / 2; i++) {
            world.addParticle(ParticleTypes.LARGE_SMOKE,
                position.x,
                position.y,
                position.z,
                (RANDOM.nextDouble() - 0.5) * 0.3,
                RANDOM.nextDouble() * 0.2,
                (RANDOM.nextDouble() - 0.5) * 0.3);
        }
    }
    
    /**
     * Spawn void realm ambient particles
     */
    public static void spawnVoidRealmAmbientParticles(ClientWorld world, BlockPos pos) {
        if (!shouldSpawnParticles()) return;
        if (RANDOM.nextFloat() > 0.1) return;
        
        double x = pos.getX() + RANDOM.nextDouble();
        double y = pos.getY() + RANDOM.nextDouble();
        double z = pos.getZ() + RANDOM.nextDouble();
        
        // Reversed portal particles
        world.addParticle(ParticleTypes.REVERSE_PORTAL,
            x, y, z,
            0, -0.1, 0);
        
        // Soul particles for eerie effect
        if (RANDOM.nextFloat() < 0.3 * CorruptionConfig.particleDensityMultiplier) {
            world.addParticle(ParticleTypes.SOUL,
                x, y, z,
                (RANDOM.nextDouble() - 0.5) * 0.05,
                0.02,
                (RANDOM.nextDouble() - 0.5) * 0.05);
        }
    }
    
    /**
     * Check if particles should spawn based on config
     */
    private static boolean shouldSpawnParticles() {
        return CorruptionConfig.particleDensityMultiplier > 0 
            && CorruptionConfig.enableBlockAnimations;
    }
}
