package com.corruptionmod;

import com.corruptionmod.client.renderer.HarbingerRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

/**
 * Client-side initialization for the Corruption Mod
 */
public class CorruptionModClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        // Register entity renderers
        EntityRendererRegistry.register(ModEntities.HARBINGER, HarbingerRenderer::new);
        
        CorruptionMod.LOGGER.info("Corruption Mod client initialized!");
    }
}
