package com.corruptionmod.dialogue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a single dialogue node with multilingual text and options.
 */
public class DialogueNode {
    private String nodeId;
    private Map<String, String> text; // Language -> Text
    private List<DialogueOption> options;
    private String questId; // Optional quest reference
    private DialogueCondition condition;
    
    public DialogueNode(String nodeId, Map<String, String> text) {
        this.nodeId = nodeId;
        this.text = text;
        this.options = new ArrayList<>();
    }
    
    public String getNodeId() {
        return nodeId;
    }
    
    public String getText(String language) {
        return text.getOrDefault(language, text.get("en"));
    }
    
    public List<DialogueOption> getOptions() {
        return options;
    }
    
    public void addOption(DialogueOption option) {
        this.options.add(option);
    }
    
    public String getQuestId() {
        return questId;
    }
    
    public void setQuestId(String questId) {
        this.questId = questId;
    }
    
    public DialogueCondition getCondition() {
        return condition;
    }
    
    public void setCondition(DialogueCondition condition) {
        this.condition = condition;
    }
    
    /**
     * Dialogue option that can lead to another node or perform an action.
     */
    public static class DialogueOption {
        private Map<String, String> text; // Language -> Text
        private String nextNodeId;
        private String action; // Optional action (e.g., "start_quest", "complete_quest")
        private DialogueCondition condition;
        
        public DialogueOption(Map<String, String> text, String nextNodeId) {
            this.text = text;
            this.nextNodeId = nextNodeId;
        }
        
        public String getText(String language) {
            return text.getOrDefault(language, text.get("en"));
        }
        
        public String getNextNodeId() {
            return nextNodeId;
        }
        
        public String getAction() {
            return action;
        }
        
        public void setAction(String action) {
            this.action = action;
        }
        
        public DialogueCondition getCondition() {
            return condition;
        }
        
        public void setCondition(DialogueCondition condition) {
            this.condition = condition;
        }
    }
    
    /**
     * Condition for showing dialogue nodes/options.
     */
    public interface DialogueCondition {
        boolean test(net.minecraft.entity.player.PlayerEntity player);
    }
}
