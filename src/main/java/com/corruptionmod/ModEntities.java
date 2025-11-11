package com.corruptionmod;

import com.corruptionmod.entity.HarbingerEntity;
import com.corruptionmod.entity.StrangerEntity;
import com.corruptionmod.entity.XynorEntity;
import com.corruptionmod.entity.TentacleEntity;
import com.corruptionmod.entity.corrupted.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Enregistre les entités du mod.
 */
public class ModEntities {
    // Boss entities
    public static EntityType<HarbingerEntity> HARBINGER;
    
    // Original entities
    public static EntityType<StrangerEntity> STRANGER;
    public static EntityType<XynorEntity> XYNOR;
    public static EntityType<TentacleEntity> TENTACLE;

    // Corrupted mob entities
    public static EntityType<CorruptedZombieEntity> CORRUPTED_ZOMBIE;
    public static EntityType<CorruptedSpiderEntity> CORRUPTED_SPIDER;
    public static EntityType<CorruptedCreeperEntity> CORRUPTED_CREEPER;
    public static EntityType<TaintedCowEntity> TAINTED_COW;
    public static EntityType<HollowedVillagerEntity> HOLLOWED_VILLAGER;

    // Méthode appelée au démarrage du mod
    public static void register() {
        // Register Harbinger boss
        HARBINGER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(CorruptionMod.MOD_ID, "harbinger"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HarbingerEntity::new)
                .dimensions(EntityDimensions.fixed(1.5f, 3.5f)) // Large imposing boss
                .fireImmune()
                .build()
        );
        
        // Register corrupted entities
        CORRUPTED_ZOMBIE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(CorruptionMod.MOD_ID, "corrupted_zombie"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CorruptedZombieEntity::new)
                .dimensions(EntityDimensions.fixed(0.6f, 1.95f))
                .build()
        );

        CORRUPTED_SPIDER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(CorruptionMod.MOD_ID, "corrupted_spider"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CorruptedSpiderEntity::new)
                .dimensions(EntityDimensions.fixed(1.4f, 0.9f))
                .build()
        );

        CORRUPTED_CREEPER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(CorruptionMod.MOD_ID, "corrupted_creeper"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CorruptedCreeperEntity::new)
                .dimensions(EntityDimensions.fixed(0.6f, 1.7f))
                .build()
        );

        TAINTED_COW = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(CorruptionMod.MOD_ID, "tainted_cow"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TaintedCowEntity::new)
                .dimensions(EntityDimensions.fixed(0.9f, 1.4f))
                .build()
        );

        HOLLOWED_VILLAGER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(CorruptionMod.MOD_ID, "hollowed_villager"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HollowedVillagerEntity::new)
                .dimensions(EntityDimensions.fixed(0.6f, 1.95f))
                .build()
        );

        // Register entity attributes
        FabricDefaultAttributeRegistry.register(HARBINGER, HarbingerEntity.createHarbingerAttributes());
        FabricDefaultAttributeRegistry.register(CORRUPTED_ZOMBIE, CorruptedZombieEntity.createCorruptedZombieAttributes());
        FabricDefaultAttributeRegistry.register(CORRUPTED_SPIDER, CorruptedSpiderEntity.createCorruptedSpiderAttributes());
        FabricDefaultAttributeRegistry.register(CORRUPTED_CREEPER, CorruptedCreeperEntity.createCorruptedCreeperAttributes());
        FabricDefaultAttributeRegistry.register(TAINTED_COW, TaintedCowEntity.createTaintedCowAttributes());
        FabricDefaultAttributeRegistry.register(HOLLOWED_VILLAGER, HollowedVillagerEntity.createHollowedVillagerAttributes());
    }
}
