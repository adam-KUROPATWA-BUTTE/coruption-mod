package com.corruptionmod;

import com.corruptionmod.event.WorldCorruptionTicker;
import com.corruptionmod.world.VoidRealmAmbientEffects;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CorruptionMod implements ModInitializer {
    public static final String MOD_ID = "corruptionmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing The Corruption Mod...");
        
        // Enregistrer les blocs
        ModBlocks.register();
        
        // Enregistrer les items
        ModItems.register();
        
        // Enregistrer les effets
        ModEffects.register();
        
        // Enregistrer les entités
        ModEntities.register();
        
        // Enregistrer les dimensions
        ModDimensions.register();
        
        // Enregistrer la génération de monde
        ModWorldGen.register();
    // Enregistrer les commandes
    com.corruptionmod.command.ModCommands.register();

    // Enregistrer le manager de purification
    com.corruptionmod.event.PurificationManager.register();
    
        // Enregistrer les effets ambiants du Void Realm
        VoidRealmAmbientEffects.register();

        // Enregistrer les événements de tick pour la corruption
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            WorldCorruptionTicker.tick(world);
        });
        
        LOGGER.info("The Corruption Mod initialized successfully!");
    }
}