package com.corruptionmod.entity.corrupted;

import com.corruptionmod.ModBlocks;
import com.corruptionmod.entity.ai.ApplyCorruptionEffectGoal;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Corrupted Zombie - Can corrupt blocks on contact and leaves corruption trail
 */
public class CorruptedZombieEntity extends ZombieEntity {
    
    private int trailTickCounter = 0;
    private static final int TRAIL_INTERVAL = 20; // Every second
    
    public CorruptedZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createCorruptedZombieAttributes() {
        return ZombieEntity.createZombieAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new ApplyCorruptionEffectGoal(this, 1, 60)); // Level II, every 3 seconds
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new ZombieAttackGoal(this, 1.0, false));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeTargetGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient()) {
            // Client-side particles
            if (this.random.nextInt(3) == 0) {
                this.getWorld().addParticle(
                    ParticleTypes.PORTAL,
                    this.getX() + (this.random.nextDouble() - 0.5) * this.getWidth(),
                    this.getY() + this.random.nextDouble() * this.getHeight(),
                    this.getZ() + (this.random.nextDouble() - 0.5) * this.getWidth(),
                    0, 0.05, 0
                );
            }
            return;
        }

        // Server-side corruption logic
        trailTickCounter++;
        
        // Leave corruption trail while walking
        if (trailTickCounter >= TRAIL_INTERVAL && this.getVelocity().lengthSquared() > 0.01) {
            trailTickCounter = 0;
            leaveCorruptionTrail();
        }

        // Corrupt blocks on contact
        if (this.age % 40 == 0) {
            corruptNearbyBlocks();
        }
    }

    private void leaveCorruptionTrail() {
        BlockPos pos = this.getBlockPos().down();
        BlockState currentState = this.getWorld().getBlockState(pos);
        
        if (canCorruptBlock(currentState)) {
            this.getWorld().setBlockState(pos, ModBlocks.CORRUPTED_DIRT.getDefaultState());
            spawnCorruptionParticles(pos);
        }
    }

    private void corruptNearbyBlocks() {
        BlockPos center = this.getBlockPos();
        
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && y == 0 && z == 0) continue;
                    
                    BlockPos pos = center.add(x, y, z);
                    BlockState state = this.getWorld().getBlockState(pos);
                    
                    if (this.random.nextInt(10) == 0 && canCorruptBlock(state)) {
                        BlockState corruptedState = getCorruptedVariant(state);
                        if (corruptedState != null) {
                            this.getWorld().setBlockState(pos, corruptedState);
                            spawnCorruptionParticles(pos);
                        }
                    }
                }
            }
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

    private void spawnCorruptionParticles(BlockPos pos) {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                ParticleTypes.SMOKE,
                pos.getX() + 0.5,
                pos.getY() + 1.0,
                pos.getZ() + 0.5,
                5,
                0.3, 0.3, 0.3,
                0.02
            );
        }
    }
}
