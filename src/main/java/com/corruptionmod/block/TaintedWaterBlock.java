package com.corruptionmod.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Tainted water block corrupted by The Void.
 * Damages players and entities on contact.
 */
public class TaintedWaterBlock extends CorruptionBlock {
    public TaintedWaterBlock() {
        super(AbstractBlock.Settings.create().mapColor(MapColor.WATER_BLUE).noCollision().strength(100f));
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        // Damage living entities that touch tainted water
        if (!world.isClient && entity instanceof LivingEntity) {
            // Deal 1 damage (half heart) every tick the entity is in contact
            // This is quite damaging, so entities will take significant damage if they stay in it
            entity.damage(world.getDamageSources().magic(), 1.0f);
        }
    }
}
