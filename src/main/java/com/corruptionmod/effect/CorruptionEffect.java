package com.corruptionmod.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;

/**
 * Corruption status effect with 4 levels (I-IV)
 * Applies progressive debuffs based on amplifier level
 */
public class CorruptionEffect extends StatusEffect {
    
    public CorruptionEffect() {
        super(StatusEffectCategory.HARMFUL, 0x5A1E5C); // Purple/dark color
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.getWorld().isClient()) {
            return;
        }

        // Apply progressive debuffs based on corruption level (amplifier 0-3 = levels I-IV)
        int level = amplifier + 1; // 1-4
        
        // Level I (amplifier 0): Minor effects
        if (level >= 1 && entity.age % 80 == 0) {
            entity.damage(entity.getDamageSources().magic(), 0.5f);
        }
        
        // Level II (amplifier 1): Movement slowdown
        if (level >= 2 && entity.age % 60 == 0) {
            entity.damage(entity.getDamageSources().magic(), 1.0f);
        }
        
        // Level III (amplifier 2): Significant damage
        if (level >= 3 && entity.age % 40 == 0) {
            entity.damage(entity.getDamageSources().magic(), 1.5f);
        }
        
        // Level IV (amplifier 3): Severe corruption
        if (level >= 4 && entity.age % 20 == 0) {
            entity.damage(entity.getDamageSources().magic(), 2.0f);
        }

        // Spawn corruption particles
        if (entity.getWorld() instanceof ServerWorld serverWorld) {
            if (entity.age % 10 == 0) {
                int particleCount = level; // More particles at higher levels
                serverWorld.spawnParticles(
                    ParticleTypes.PORTAL,
                    entity.getX(),
                    entity.getY() + entity.getHeight() / 2,
                    entity.getZ(),
                    particleCount,
                    0.3, 0.5, 0.3,
                    0.02
                );
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // Apply effect every tick
        return true;
    }
}
