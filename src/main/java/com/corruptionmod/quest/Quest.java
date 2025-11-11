package com.corruptionmod.quest;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a quest with objectives, rewards, and progress tracking.
 */
public class Quest {
    private final String questId;
    private final Map<String, String> name; // Language -> Name
    private final Map<String, String> description; // Language -> Description
    private final List<QuestObjective> objectives;
    private final List<ItemStack> rewards;
    private final int xpReward;
    private final String prerequisiteQuest;
    private final String nextQuest;
    private QuestState state;
    
    public Quest(String questId, Map<String, String> name, Map<String, String> description,
                 List<QuestObjective> objectives, List<ItemStack> rewards, int xpReward,
                 String prerequisiteQuest, String nextQuest) {
        this.questId = questId;
        this.name = name;
        this.description = description;
        this.objectives = objectives;
        this.rewards = rewards;
        this.xpReward = xpReward;
        this.prerequisiteQuest = prerequisiteQuest;
        this.nextQuest = nextQuest;
        this.state = QuestState.NOT_STARTED;
    }
    
    public String getQuestId() {
        return questId;
    }
    
    public String getName(String language) {
        return name.getOrDefault(language, name.get("en"));
    }
    
    public String getDescription(String language) {
        return description.getOrDefault(language, description.get("en"));
    }
    
    public List<QuestObjective> getObjectives() {
        return objectives;
    }
    
    public List<ItemStack> getRewards() {
        return rewards;
    }
    
    public int getXpReward() {
        return xpReward;
    }
    
    public String getPrerequisiteQuest() {
        return prerequisiteQuest;
    }
    
    public String getNextQuest() {
        return nextQuest;
    }
    
    public QuestState getState() {
        return state;
    }
    
    public void setState(QuestState state) {
        this.state = state;
    }
    
    /**
     * Checks if all objectives are complete.
     */
    public boolean isComplete() {
        for (QuestObjective objective : objectives) {
            if (!objective.isComplete()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Updates quest progress based on an event.
     */
    public void updateProgress(PlayerEntity player, String objectiveType, Object data) {
        for (QuestObjective objective : objectives) {
            if (objective.getType().equals(objectiveType)) {
                objective.updateProgress(data);
            }
        }
        
        // Check if quest is now complete
        if (isComplete() && state == QuestState.IN_PROGRESS) {
            state = QuestState.READY_TO_COMPLETE;
        }
    }
    
    /**
     * Quest state enumeration.
     */
    public enum QuestState {
        NOT_STARTED,
        IN_PROGRESS,
        READY_TO_COMPLETE,
        COMPLETED
    }
    
    /**
     * Represents a quest objective with progress tracking.
     */
    public static class QuestObjective {
        private final String type; // e.g., "collect_item", "kill_entity", "explore_location"
        private final Map<String, String> description; // Language -> Description
        private final int required;
        private int current;
        private final Object target; // Item, entity type, location, etc.
        
        public QuestObjective(String type, Map<String, String> description, int required, Object target) {
            this.type = type;
            this.description = description;
            this.required = required;
            this.current = 0;
            this.target = target;
        }
        
        public String getType() {
            return type;
        }
        
        public String getDescription(String language) {
            return description.getOrDefault(language, description.get("en"));
        }
        
        public int getRequired() {
            return required;
        }
        
        public int getCurrent() {
            return current;
        }
        
        public void setCurrent(int current) {
            this.current = Math.min(current, required);
        }
        
        public Object getTarget() {
            return target;
        }
        
        public boolean isComplete() {
            return current >= required;
        }
        
        public void updateProgress(Object data) {
            if (target.equals(data)) {
                current++;
                if (current > required) {
                    current = required;
                }
            }
        }
        
        public String getProgressString() {
            return current + "/" + required;
        }
    }
}
