package com.corruptionmod.client.model;

import com.corruptionmod.CorruptionMod;
import com.corruptionmod.entity.HarbingerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

/**
 * GeckoLib model for The Harbinger boss entity
 */
public class HarbingerModel extends GeoModel<HarbingerEntity> {
    
    @Override
    public Identifier getModelResource(HarbingerEntity entity) {
        return new Identifier(CorruptionMod.MOD_ID, "geo/harbinger.geo.json");
    }
    
    @Override
    public Identifier getTextureResource(HarbingerEntity entity) {
        // Change texture based on phase
        int phase = entity.getPhase();
        return new Identifier(CorruptionMod.MOD_ID, 
            "textures/entity/harbinger_phase" + phase + ".png");
    }
    
    @Override
    public Identifier getAnimationResource(HarbingerEntity entity) {
        return new Identifier(CorruptionMod.MOD_ID, "animations/harbinger.animation.json");
    }
}
