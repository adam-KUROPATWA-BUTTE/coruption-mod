package com.corruptionmod.npc;

import com.corruptionmod.dialogue.DialogueManager;
import com.corruptionmod.quest.QuestManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

/**
 * Elder Maren - Quest giver and lore keeper.
 * An old villager with grey robes and glowing purple eyes.
 */
public class ElderMarenEntity extends BaseNPCEntity {
    
    public ElderMarenEntity(EntityType<? extends ElderMarenEntity> entityType, World world) {
        super(entityType, world, "elder_maren");
    }
    
    public static DefaultAttributeContainer.Builder createElderMarenAttributes() {
        return createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5);
    }
    
    @Override
    protected void openDialogue(PlayerEntity player) {
        // Determine which dialogue to show based on quest state
        String nodeId = "start";
        
        if (QuestManager.hasCompletedQuest(player, "quest_1_first_infection")) {
            if (!QuestManager.hasCompletedQuest(player, "quest_2_echoes_past")) {
                nodeId = "quest_2_available";
            } else if (!QuestManager.hasCompletedQuest(player, "quest_4_into_void")) {
                nodeId = "quest_4_available";
            } else {
                nodeId = "completed";
            }
        }
        
        // In a real implementation, this would open a GUI
        // For now, we'll just send a message
        player.sendMessage(Text.literal("Elder Maren: " + nodeId), false);
    }
    
    @Override
    public Text getName() {
        return Text.translatable("entity.corruptionmod.elder_maren");
    }
}
