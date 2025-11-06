package com.corruptionmod.entity.ai;

import com.corruptionmod.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;

import java.util.EnumSet;

/**
 * AI Goal that applies corruption status effect to target entities
 */
public class ApplyCorruptionEffectGoal extends Goal {
    private final MobEntity mob;
    private final int corruptionLevel; // 0-3 for levels I-IV
    private final int applicationInterval; // Ticks between applications
    private int tickCounter = 0;

    public ApplyCorruptionEffectGoal(MobEntity mob, int corruptionLevel, int applicationInterval) {
        this.mob = mob;
        this.corruptionLevel = Math.min(3, Math.max(0, corruptionLevel));
        this.applicationInterval = applicationInterval;
        this.setControls(EnumSet.of(Control.LOOK));
    }

    @Override
    public boolean canStart() {
        LivingEntity target = mob.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public void tick() {
        LivingEntity target = mob.getTarget();
        if (target == null) {
            return;
        }

        tickCounter++;

        if (tickCounter >= applicationInterval) {
            tickCounter = 0;

            // Apply corruption effect if close enough
            double distanceSquared = mob.squaredDistanceTo(target);
            if (distanceSquared <= 16.0) { // Within 4 blocks
                StatusEffectInstance corruption = new StatusEffectInstance(
                    ModEffects.CORRUPTION,
                    200, // 10 seconds duration
                    corruptionLevel,
                    false,
                    true
                );
                target.addStatusEffect(corruption);
            }
        }
    }
}
