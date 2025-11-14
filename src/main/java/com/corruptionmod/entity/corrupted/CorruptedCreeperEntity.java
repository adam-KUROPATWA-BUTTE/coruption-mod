package com.corruptionmod.entity.corrupted;

import com.corruptionmod.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

/**
 * Corrupted Creeper - Explosion spreads corruption with larger radius
 */
public class CorruptedCreeperEntity extends CreeperEntity {
    
    private static final float EXPLOSION_RADIUS = 4.0f; // Larger than normal
    
    public CorruptedCreeperEntity(EntityType<? extends CreeperEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createCorruptedCreeperAttributes() {
        return CreeperEntity.createCreeperAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 22.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.27);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new CreeperIgniteGoal(this));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, PlayerEntity.class, 6.0f, 1.0, 1.2));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient()) {
            // Client-side particles - purple/dark effects
            if (this.random.nextInt(3) == 0) {
                this.getWorld().addParticle(
                    ParticleTypes.PORTAL,
                    this.getX() + (this.random.nextDouble() - 0.5) * this.getWidth(),
                    this.getY() + this.random.nextDouble() * this.getHeight(),
                    this.getZ() + (this.random.nextDouble() - 0.5) * this.getWidth(),
                    0, 0.05, 0
                );
            }
            
            // Extra particles when charged
            if (this.getFuseSpeed() > 0) {
                this.getWorld().addParticle(
                    ParticleTypes.SMOKE,
                    this.getX(),
                    this.getY() + this.getHeight(),
                    this.getZ(),
                    0, 0.1, 0
                );
            }
        }
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        
        if (!this.getWorld().isClient()) {
            // Spread corruption in explosion area
            corruptExplosionArea();
        }
    }

    private void corruptExplosionArea() {
        BlockPos center = this.getBlockPos();
        int radius = (int) (EXPLOSION_RADIUS + 1);
        
        for (BlockPos pos : BlockPos.iterate(
            center.add(-radius, -radius, -radius),
            center.add(radius, radius, radius)
        )) {
            double distance = pos.getSquaredDistance(center);
            
            if (distance <= EXPLOSION_RADIUS * EXPLOSION_RADIUS) {
                BlockState state = this.getWorld().getBlockState(pos);
                
                // Higher chance to corrupt blocks closer to center
                double corruptionChance = 1.0 - (distance / (EXPLOSION_RADIUS * EXPLOSION_RADIUS));
                
                if (this.random.nextDouble() < corruptionChance && canCorruptBlock(state)) {
                    BlockState corruptedState = getCorruptedVariant(state);
                    if (corruptedState != null) {
                        this.getWorld().setBlockState(pos, corruptedState);
                    }
                }
            }
        }
        
        // Spawn corruption particles
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                ParticleTypes.LARGE_SMOKE,
                center.getX() + 0.5,
                center.getY() + 0.5,
                center.getZ() + 0.5,
                50,
                EXPLOSION_RADIUS / 2, EXPLOSION_RADIUS / 2, EXPLOSION_RADIUS / 2,
                0.1
            );
        }
    }

    private boolean canCorruptBlock(BlockState state) {
        return state.isOf(Blocks.GRASS_BLOCK) ||
               state.isOf(Blocks.DIRT) ||
               state.isOf(Blocks.STONE) ||
               state.isOf(Blocks.SAND) ||
               state.isOf(Blocks.OAK_LEAVES) ||
               state.isOf(Blocks.BIRCH_LEAVES) ||
               state.isOf(Blocks.SPRUCE_LEAVES);
    }

    private BlockState getCorruptedVariant(BlockState state) {
        if (state.isOf(Blocks.GRASS_BLOCK)) {
            return ModBlocks.CORRUPTED_GRASS.getDefaultState();
        } else if (state.isOf(Blocks.DIRT)) {
            return ModBlocks.CORRUPTED_DIRT.getDefaultState();
        } else if (state.isOf(Blocks.STONE)) {
            return ModBlocks.CORRUPTED_STONE.getDefaultState();
        } else if (state.isOf(Blocks.SAND)) {
            return ModBlocks.CORRUPTED_SAND.getDefaultState();
        } else if (state.isOf(Blocks.OAK_LEAVES) || 
                   state.isOf(Blocks.BIRCH_LEAVES) || 
                   state.isOf(Blocks.SPRUCE_LEAVES)) {
            return ModBlocks.WITHERED_LEAVES.getDefaultState();
        }
        return null;
    }

    @Override
    protected float getExplosionPower() {
        return EXPLOSION_RADIUS;
    }
}
