package com.corruptionmod.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

/**
 * Xynor, Tisseur d'Ombres : boss à phases multiples.
 */
public class XynorEntity extends MobEntity {
    public XynorEntity(EntityType<? extends MobEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();
        // Logique de phases, attaques, invocations, téléportation
    }
}
