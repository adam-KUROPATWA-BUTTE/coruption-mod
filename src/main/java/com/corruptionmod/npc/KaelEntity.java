package com.corruptionmod.npc;

import com.corruptionmod.quest.QuestManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

/**
 * Kael the Wanderer - Combat trainer and corruption survivor.
 * Wears worn leather armor with corruption scars.
 */
public class KaelEntity extends BaseNPCEntity {
    
    public KaelEntity(EntityType<? extends KaelEntity> entityType, World world) {
        super(entityType, world, "kael");
    }
    
    public static DefaultAttributeContainer.Builder createKaelAttributes() {
        return createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6)
            .add(EntityAttributes.GENERIC_ARMOR, 5.0);
    }
    
    @Override
    protected void openDialogue(PlayerEntity player) {
        // Determine which dialogue to show based on quest state
        String nodeId = "start";
        
        if (QuestManager.hasCompletedQuest(player, "quest_2_echoes_past")) {
            if (!QuestManager.hasCompletedQuest(player, "quest_3_cleansing")) {
                nodeId = "quest_3_available";
            } else {
                nodeId = "completed";
            }
        }
        
        // In a real implementation, this would open a GUI
        player.sendMessage(Text.literal("Kael: " + nodeId), false);
    }
    
    @Override
    public Text getName() {
        return Text.translatable("entity.corruptionmod.kael");
    }
}
