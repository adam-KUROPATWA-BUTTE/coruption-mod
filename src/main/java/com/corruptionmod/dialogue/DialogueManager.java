package com.corruptionmod.dialogue;

import com.google.gson.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages dialogue for NPCs, including loading from JSON and tracking active dialogues.
 */
public class DialogueManager {
    private static final Logger LOGGER = LoggerFactory.getLogger("CorruptionMod-DialogueManager");
    private static final Map<String, Map<String, DialogueNode>> NPC_DIALOGUES = new HashMap<>();
    private static final Map<PlayerEntity, String> ACTIVE_DIALOGUES = new HashMap<>();
    
    /**
     * Loads dialogue data for an NPC from JSON.
     */
    public static void loadDialogue(String npcId, InputStream inputStream) {
        try {
            JsonObject json = JsonParser.parseReader(new InputStreamReader(inputStream)).getAsJsonObject();
            Map<String, DialogueNode> nodes = new HashMap<>();
            
            JsonObject nodesJson = json.getAsJsonObject("nodes");
            for (Map.Entry<String, JsonElement> entry : nodesJson.entrySet()) {
                String nodeId = entry.getKey();
                JsonObject nodeData = entry.getValue().getAsJsonObject();
                
                // Parse text for different languages
                Map<String, String> text = new HashMap<>();
                JsonObject textJson = nodeData.getAsJsonObject("text");
                for (Map.Entry<String, JsonElement> textEntry : textJson.entrySet()) {
                    text.put(textEntry.getKey(), textEntry.getValue().getAsString());
                }
                
                DialogueNode node = new DialogueNode(nodeId, text);
                
                // Parse quest ID if present
                if (nodeData.has("quest_id")) {
                    node.setQuestId(nodeData.get("quest_id").getAsString());
                }
                
                // Parse options
                if (nodeData.has("options")) {
                    JsonArray optionsJson = nodeData.getAsJsonArray("options");
                    for (JsonElement optionElement : optionsJson) {
                        JsonObject optionJson = optionElement.getAsJsonObject();
                        
                        Map<String, String> optionText = new HashMap<>();
                        JsonObject optionTextJson = optionJson.getAsJsonObject("text");
                        for (Map.Entry<String, JsonElement> textEntry : optionTextJson.entrySet()) {
                            optionText.put(textEntry.getKey(), textEntry.getValue().getAsString());
                        }
                        
                        String nextNodeId = optionJson.has("next_node") ? 
                            optionJson.get("next_node").getAsString() : null;
                        
                        DialogueNode.DialogueOption option = new DialogueNode.DialogueOption(optionText, nextNodeId);
                        
                        if (optionJson.has("action")) {
                            option.setAction(optionJson.get("action").getAsString());
                        }
                        
                        node.addOption(option);
                    }
                }
                
                nodes.put(nodeId, node);
            }
            
            NPC_DIALOGUES.put(npcId, nodes);
            LOGGER.info("Loaded dialogue for NPC: {}", npcId);
        } catch (Exception e) {
            LOGGER.error("Failed to load dialogue for NPC: {}", npcId, e);
        }
    }
    
    /**
     * Gets a dialogue node for an NPC.
     */
    public static DialogueNode getDialogueNode(String npcId, String nodeId) {
        Map<String, DialogueNode> nodes = NPC_DIALOGUES.get(npcId);
        if (nodes != null) {
            return nodes.get(nodeId);
        }
        return null;
    }
    
    /**
     * Gets the starting dialogue node for an NPC.
     */
    public static DialogueNode getStartingNode(String npcId) {
        return getDialogueNode(npcId, "start");
    }
    
    /**
     * Sets the active dialogue for a player.
     */
    public static void setActiveDialogue(PlayerEntity player, String nodeId) {
        ACTIVE_DIALOGUES.put(player, nodeId);
    }
    
    /**
     * Gets the active dialogue for a player.
     */
    public static String getActiveDialogue(PlayerEntity player) {
        return ACTIVE_DIALOGUES.get(player);
    }
    
    /**
     * Clears the active dialogue for a player.
     */
    public static void clearActiveDialogue(PlayerEntity player) {
        ACTIVE_DIALOGUES.remove(player);
    }
    
    /**
     * Registers all NPC dialogues.
     */
    public static void register() {
        LOGGER.info("Registering NPC dialogues...");
        // Dialogues will be loaded from resource files
    }
}
