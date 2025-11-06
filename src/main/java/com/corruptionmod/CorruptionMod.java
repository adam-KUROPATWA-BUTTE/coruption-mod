package com.corruptionmod;

import com.corruptionmod.event.WorldCorruptionTicker;
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
        
        // Register blocks
        ModBlocks.register();
        
        // Register items
        ModItems.register();
        
        // Register effects
        ModEffects.register();
        
        // Register entities
        ModEntities.register();
        
        // Register dimensions
        ModDimensions.register();
        
        // Register world generation
        ModWorldGen.register();
        
        // Register commands
        com.corruptionmod.command.ModCommands.register();

        // Register purification manager
        com.corruptionmod.event.PurificationManager.register();

        // Register tick events for corruption
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            WorldCorruptionTicker.tick(world);
        });
        
        LOGGER.info("The Corruption Mod initialized successfully!");
    }
}