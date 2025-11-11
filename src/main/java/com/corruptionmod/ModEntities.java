package com.corruptionmod;

import com.corruptionmod.entity.StrangerEntity;
import com.corruptionmod.entity.XynorEntity;
import com.corruptionmod.entity.TentacleEntity;
import com.corruptionmod.entity.corrupted.*;
import com.corruptionmod.npc.ElderMarenEntity;
import com.corruptionmod.npc.KaelEntity;
import com.corruptionmod.npc.HollowOneEntity;
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
    
    // NPC entities
    public static EntityType<ElderMarenEntity> ELDER_MAREN;
    public static EntityType<KaelEntity> KAEL;
    public static EntityType<HollowOneEntity> HOLLOW_ONE;

    // Méthode appelée au démarrage du mod
    public static void register() {
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
        FabricDefaultAttributeRegistry.register(CORRUPTED_ZOMBIE, CorruptedZombieEntity.createCorruptedZombieAttributes());
        FabricDefaultAttributeRegistry.register(CORRUPTED_SPIDER, CorruptedSpiderEntity.createCorruptedSpiderAttributes());
        FabricDefaultAttributeRegistry.register(CORRUPTED_CREEPER, CorruptedCreeperEntity.createCorruptedCreeperAttributes());
        FabricDefaultAttributeRegistry.register(TAINTED_COW, TaintedCowEntity.createTaintedCowAttributes());
        FabricDefaultAttributeRegistry.register(HOLLOWED_VILLAGER, HollowedVillagerEntity.createHollowedVillagerAttributes());
        
        // Register NPC entities
        ELDER_MAREN = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(CorruptionMod.MOD_ID, "elder_maren"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ElderMarenEntity::new)
                .dimensions(EntityDimensions.fixed(0.6f, 1.95f))
                .build()
        );
        
        KAEL = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(CorruptionMod.MOD_ID, "kael"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, KaelEntity::new)
                .dimensions(EntityDimensions.fixed(0.6f, 1.95f))
                .build()
        );
        
        HOLLOW_ONE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(CorruptionMod.MOD_ID, "hollow_one"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HollowOneEntity::new)
                .dimensions(EntityDimensions.fixed(0.6f, 1.95f))
                .build()
        );
        
        // Register NPC attributes
        FabricDefaultAttributeRegistry.register(ELDER_MAREN, ElderMarenEntity.createElderMarenAttributes());
        FabricDefaultAttributeRegistry.register(KAEL, KaelEntity.createKaelAttributes());
        FabricDefaultAttributeRegistry.register(HOLLOW_ONE, HollowOneEntity.createHollowOneAttributes());
    }
}
