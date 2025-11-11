package com.corruptionmod.client.renderer;

import com.corruptionmod.client.model.HarbingerModel;
import com.corruptionmod.entity.HarbingerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/**
 * GeckoLib renderer for The Harbinger boss entity
 */
public class HarbingerRenderer extends GeoEntityRenderer<HarbingerEntity> {
    
    public HarbingerRenderer(EntityRendererFactory.Context context) {
        super(context, new HarbingerModel());
        this.shadowRadius = 1.5f; // Large shadow for imposing boss
    }
    
    @Override
    public float getMotionAnimThreshold(HarbingerEntity animatable) {
        return 0.005f; // Very sensitive motion detection for smooth animations
    }
}
