package com.corruptionmod.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

/**
 * Tentacule du Néant : mini-mob invoqué par Xynor.
 */
public class TentacleEntity extends MobEntity {
    public TentacleEntity(EntityType<? extends MobEntity> type, World world) {
        super(type, world);
    }
}
