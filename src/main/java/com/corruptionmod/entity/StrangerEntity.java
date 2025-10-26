package com.corruptionmod.entity;

import com.corruptionmod.util.DialogueManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/**
 * NPC Stranger - Le Survivant
 * Un PNJ qui donne des informations sur la corruption
 */
public class StrangerEntity extends PathAwareEntity {
    private int dialogueIndex = 0;
    private long lastMessageTick = 0;
    private long vanishAtTick = -1;
    private static final long MESSAGE_COOLDOWN = 20L * 10L; // 10 seconds

    public StrangerEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        // Reste essentiellement statique
        this.setNoAi(true);
    }

    public static DefaultAttributeContainer.Builder createStrangerAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0);
    }

    @Override
    protected void initGoals() {
        // Pas de goals de déplacement — PNJ statique
        this.goalSelector.add(0, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(1, new LookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.world.isClient) return;

        long time = this.world.getTime();

        // Planifier la disparition si pas déjà fait
        if (vanishAtTick < 0) {
            // Disparaît après 20-120 secondes aléatoires
            long lifetime = 20L * (20L + this.random.nextInt(100));
            vanishAtTick = time + lifetime;
        }

        // Si joueur proche (5 blocs)
        PlayerEntity nearest = this.world.getClosestPlayer(this, 5.0);
        if (nearest != null) {
            if (time - lastMessageTick >= MESSAGE_COOLDOWN) {
                lastMessageTick = time;
                String msg = DialogueManager.getRandomDialogue();
                // Envoi direct au joueur
                nearest.sendMessage(net.minecraft.text.Text.literal(msg), false);

                // Son et particules
                if (this.world instanceof net.minecraft.server.world.ServerWorld serverWorld) {
                    serverWorld.spawnParticles(net.minecraft.particle.ParticleTypes.PORTAL, this.getX(), this.getY() + 1.0, this.getZ(), 10, 0.3, 0.5, 0.3, 0.02);
                    serverWorld.playSound(null, this.getBlockPos(), net.minecraft.sound.SoundEvents.ENTITY_VILLAGER_AMBIENT, net.minecraft.sound.SoundCategory.NEUTRAL, 1.0f, 1.0f);
                }
            }
        }

        // Disparition programmée
        if (time >= vanishAtTick) {
            if (this.world instanceof net.minecraft.server.world.ServerWorld serverWorld) {
                // Particules de fumée
                serverWorld.spawnParticles(net.minecraft.particle.ParticleTypes.SMOKE, this.getX(), this.getY() + 1.0, this.getZ(), 40, 0.6, 0.8, 0.6, 0.05);
                // Remplacer par un bloc de corruption à sa position
                serverWorld.setBlockState(this.getBlockPos(), com.corruptionmod.ModBlocks.CORRUPTION_BLOCK.getDefaultState());

                // Message global
                net.minecraft.text.Text global = net.minecraft.text.Text.literal("§4⚠ Une présence inconnue a quitté la zone du spawn...");
                var server = this.world.getServer();
                if (server != null) {
                    server.getPlayerManager().getPlayerList().forEach(p -> p.sendMessage(global, false));
                }
            }
            this.discard();
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.getWorld().isClient) {
            // Affiche un dialogue du Stranger
            String message = DialogueManager.getStrangerDialogue(dialogueIndex, player);
            player.sendMessage(net.minecraft.text.Text.literal(message), false);
            
            // Cycle à travers les dialogues
            dialogueIndex = (dialogueIndex + 1) % 5;
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }
}