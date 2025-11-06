package com.corruptionmod.entity.corrupted;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

/**
 * Tainted Cow - Corrupted version of passive cow, now aggressive
 */
public class TaintedCowEntity extends PathAwareEntity {
    
    public TaintedCowEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createTaintedCowAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.22);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeTargetGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient()) {
            // Client-side corruption particles
            if (this.random.nextInt(5) == 0) {
                this.getWorld().addParticle(
                    ParticleTypes.SMOKE,
                    this.getX() + (this.random.nextDouble() - 0.5) * this.getWidth(),
                    this.getY() + this.random.nextDouble() * this.getHeight(),
                    this.getZ() + (this.random.nextDouble() - 0.5) * this.getWidth(),
                    0, 0.02, 0
                );
            }
        }
    }
}
