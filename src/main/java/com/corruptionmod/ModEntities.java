package com.corruptionmod;

import com.corruptionmod.entity.StrangerEntity;
import com.corruptionmod.entity.XynorEntity;
import com.corruptionmod.entity.TentacleEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Enregistre les entités du mod.
 */
public class ModEntities {
    public static EntityType<StrangerEntity> STRANGER;
    public static EntityType<XynorEntity> XYNOR;
    public static EntityType<TentacleEntity> TENTACLE;

    // Méthode appelée au démarrage du mod
    public static void register() {
        // ...enregistrement des entités (voir Fabric EntityTypeBuilder)...
        // L'implémentation détaillée des EntityType se fera plus tard.
    }
}
