package com.corruptionmod.npc;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/**
 * Base NPC entity class for quest-giving NPCs.
 * Provides common functionality for all NPCs in the mod.
 */
public abstract class BaseNPCEntity extends PathAwareEntity {
    
    protected String npcId;
    
    protected BaseNPCEntity(EntityType<? extends PathAwareEntity> entityType, World world, String npcId) {
        super(entityType, world);
        this.npcId = npcId;
        this.setPersistent();
    }
    
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.6));
        this.goalSelector.add(4, new LookAroundGoal(this));
    }
    
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.getWorld().isClient) {
            // Open dialogue on server side
            openDialogue(player);
            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;
    }
    
    /**
     * Opens the dialogue UI for the player.
     * To be implemented by subclasses.
     */
    protected abstract void openDialogue(PlayerEntity player);
    
    /**
     * Gets the NPC's unique identifier.
     */
    public String getNpcId() {
        return npcId;
    }
    
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("NpcId", this.npcId);
    }
    
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("NpcId")) {
            this.npcId = nbt.getString("NpcId");
        }
    }
    
    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false; // NPCs should not despawn
    }
    
    @Override
    protected boolean isDisallowedInPeaceful() {
        return false; // NPCs can exist in peaceful mode
    }
}
