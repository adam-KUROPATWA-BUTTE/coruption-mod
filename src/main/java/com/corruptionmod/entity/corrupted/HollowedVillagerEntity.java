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
 * Hollowed Villager - Corrupted villager with lost normal behavior
 * Hostile or neutral depending on corruption level
 */
public class HollowedVillagerEntity extends PathAwareEntity {
    
    private int corruptionLevel = 0; // 0-2: neutral, 3-4: hostile
    
    public HollowedVillagerEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        // Random corruption level between 1-4
        this.corruptionLevel = 1 + world.random.nextInt(4);
    }

    public static DefaultAttributeContainer.Builder createHollowedVillagerAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 18.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.5)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        
        // Only add aggressive goals if highly corrupted
        if (corruptionLevel >= 3) {
            this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, false));
            this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        }
        
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.6));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient()) {
            // Client-side corruption particles - more particles at higher corruption
            if (this.random.nextInt(7 - corruptionLevel) == 0) {
                this.getWorld().addParticle(
                    ParticleTypes.PORTAL,
                    this.getX() + (this.random.nextDouble() - 0.5) * this.getWidth(),
                    this.getY() + this.random.nextDouble() * this.getHeight(),
                    this.getZ() + (this.random.nextDouble() - 0.5) * this.getWidth(),
                    0, 0.03, 0
                );
            }
        }
    }

    public int getCorruptionLevel() {
        return corruptionLevel;
    }

    public boolean isHostile() {
        return corruptionLevel >= 3;
    }
}
