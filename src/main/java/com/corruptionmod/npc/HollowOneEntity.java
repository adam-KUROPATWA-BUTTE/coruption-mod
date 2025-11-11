package com.corruptionmod.npc;

import com.corruptionmod.quest.QuestManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

/**
 * The Hollow One - Mysterious entity that appears in the Void Realm.
 * A shadowy figure with void particles and glowing eyes.
 */
public class HollowOneEntity extends BaseNPCEntity {
    
    public HollowOneEntity(EntityType<? extends HollowOneEntity> entityType, World world) {
        super(entityType, world, "hollow_one");
    }
    
    public static DefaultAttributeContainer.Builder createHollowOneAttributes() {
        return createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4);
    }
    
    @Override
    protected void openDialogue(PlayerEntity player) {
        // Determine which dialogue to show based on quest state
        String nodeId = "start";
        
        if (QuestManager.hasCompletedQuest(player, "quest_4_into_void")) {
            if (!QuestManager.hasCompletedQuest(player, "quest_5_heart_darkness")) {
                nodeId = "quest_5_available";
            } else {
                nodeId = "completed";
            }
        }
        
        // In a real implementation, this would open a GUI
        player.sendMessage(Text.literal("The Hollow One: " + nodeId), false);
    }
    
    @Override
    public Text getName() {
        return Text.translatable("entity.corruptionmod.hollow_one");
    }
}
