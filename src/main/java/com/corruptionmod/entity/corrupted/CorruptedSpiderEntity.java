package com.corruptionmod.entity.corrupted;

import com.corruptionmod.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Corrupted Spider - Places slow/corrupting webs with special mechanics
 */
public class CorruptedSpiderEntity extends SpiderEntity {
    
    private int webPlacementCounter = 0;
    private static final int WEB_PLACEMENT_INTERVAL = 60; // Every 3 seconds
    
    public CorruptedSpiderEntity(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createCorruptedSpiderAttributes() {
        return SpiderEntity.createSpiderAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(3, new PounceAtTargetGoal(this, 0.4f));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeTargetGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient()) {
            // Client-side particles
            if (this.random.nextInt(4) == 0) {
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

        // Server-side web placement
        webPlacementCounter++;
        
        if (webPlacementCounter >= WEB_PLACEMENT_INTERVAL && this.getVelocity().lengthSquared() > 0.01) {
            webPlacementCounter = 0;
            placeCorruptingWeb();
        }

        // Spread corruption from webs
        if (this.age % 80 == 0) {
            spreadCorruptionFromWebs();
        }
    }

    private void placeCorruptingWeb() {
        BlockPos pos = this.getBlockPos();
        BlockState state = this.getWorld().getBlockState(pos);
        
        if (state.isAir()) {
            this.getWorld().setBlockState(pos, Blocks.COBWEB.getDefaultState());
            
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                    ParticleTypes.SMOKE,
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    3,
                    0.2, 0.2, 0.2,
                    0.01
                );
            }
        }
    }

    private void spreadCorruptionFromWebs() {
        BlockPos center = this.getBlockPos();
        
        // Check for webs nearby and corrupt blocks around them
        for (int x = -2; x <= 2; x++) {
            for (int y = -1; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos webPos = center.add(x, y, z);
                    BlockState webState = this.getWorld().getBlockState(webPos);
                    
                    if (webState.isOf(Blocks.COBWEB)) {
                        // Corrupt blocks adjacent to the web
                        corruptBlocksAroundWeb(webPos);
                    }
                }
            }
        }
    }

    private void corruptBlocksAroundWeb(BlockPos webPos) {
        for (BlockPos adjacentPos : BlockPos.iterate(webPos.add(-1, -1, -1), webPos.add(1, 1, 1))) {
            if (adjacentPos.equals(webPos)) continue;
            
            BlockState state = this.getWorld().getBlockState(adjacentPos);
            
            if (this.random.nextInt(20) == 0 && canCorruptBlock(state)) {
                BlockState corruptedState = getCorruptedVariant(state);
                if (corruptedState != null) {
                    this.getWorld().setBlockState(adjacentPos, corruptedState);
                }
            }
        }
    }

    private boolean canCorruptBlock(BlockState state) {
        return state.isOf(Blocks.GRASS_BLOCK) ||
               state.isOf(Blocks.DIRT) ||
               state.isOf(Blocks.STONE);
    }

    private BlockState getCorruptedVariant(BlockState state) {
        if (state.isOf(Blocks.GRASS_BLOCK)) {
            return ModBlocks.CORRUPTED_GRASS.getDefaultState();
        } else if (state.isOf(Blocks.DIRT)) {
            return ModBlocks.CORRUPTED_DIRT.getDefaultState();
        } else if (state.isOf(Blocks.STONE)) {
            return ModBlocks.CORRUPTED_STONE.getDefaultState();
        }
        return null;
    }
}
