package com.corruptionmod.quest;

import com.corruptionmod.CorruptionMod;
import com.google.gson.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Manages quests for players, including tracking progress and completion.
 */
public class QuestManager {
    private static final Logger LOGGER = LoggerFactory.getLogger("CorruptionMod-QuestManager");
    private static final Map<String, Quest> ALL_QUESTS = new HashMap<>();
    private static final Map<UUID, PlayerQuestData> PLAYER_QUESTS = new HashMap<>();
    
    /**
     * Loads a quest from JSON.
     */
    public static void loadQuest(String questId, InputStream inputStream) {
        try {
            JsonObject json = JsonParser.parseReader(new InputStreamReader(inputStream)).getAsJsonObject();
            
            // Parse name and description
            Map<String, String> name = parseLanguageMap(json.getAsJsonObject("name"));
            Map<String, String> description = parseLanguageMap(json.getAsJsonObject("description"));
            
            // Parse objectives
            List<Quest.QuestObjective> objectives = new ArrayList<>();
            JsonArray objectivesJson = json.getAsJsonArray("objectives");
            for (JsonElement objElement : objectivesJson) {
                JsonObject objJson = objElement.getAsJsonObject();
                String type = objJson.get("type").getAsString();
                Map<String, String> objDesc = parseLanguageMap(objJson.getAsJsonObject("description"));
                int required = objJson.get("required").getAsInt();
                String target = objJson.has("target") ? objJson.get("target").getAsString() : "";
                
                objectives.add(new Quest.QuestObjective(type, objDesc, required, target));
            }
            
            // Parse rewards
            List<ItemStack> rewards = new ArrayList<>();
            if (json.has("rewards")) {
                JsonArray rewardsJson = json.getAsJsonArray("rewards");
                for (JsonElement rewardElement : rewardsJson) {
                    JsonObject rewardJson = rewardElement.getAsJsonObject();
                    String itemId = rewardJson.get("item").getAsString();
                    int count = rewardJson.has("count") ? rewardJson.get("count").getAsInt() : 1;
                    
                    Item item = Registries.ITEM.get(Identifier.of(itemId));
                    if (item != null) {
                        rewards.add(new ItemStack(item, count));
                    }
                }
            }
            
            int xpReward = json.has("xp_reward") ? json.get("xp_reward").getAsInt() : 0;
            String prerequisite = json.has("prerequisite") ? json.get("prerequisite").getAsString() : null;
            String nextQuest = json.has("next_quest") ? json.get("next_quest").getAsString() : null;
            
            Quest quest = new Quest(questId, name, description, objectives, rewards, xpReward, prerequisite, nextQuest);
            ALL_QUESTS.put(questId, quest);
            
            LOGGER.info("Loaded quest: {}", questId);
        } catch (Exception e) {
            LOGGER.error("Failed to load quest: {}", questId, e);
        }
    }
    
    private static Map<String, String> parseLanguageMap(JsonObject json) {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            map.put(entry.getKey(), entry.getValue().getAsString());
        }
        return map;
    }
    
    /**
     * Gets a quest by ID.
     */
    public static Quest getQuest(String questId) {
        return ALL_QUESTS.get(questId);
    }
    
    /**
     * Gets player quest data.
     */
    public static PlayerQuestData getPlayerQuestData(PlayerEntity player) {
        return PLAYER_QUESTS.computeIfAbsent(player.getUuid(), uuid -> new PlayerQuestData());
    }
    
    /**
     * Starts a quest for a player.
     */
    public static void startQuest(PlayerEntity player, String questId) {
        Quest quest = getQuest(questId);
        if (quest != null) {
            PlayerQuestData data = getPlayerQuestData(player);
            data.startQuest(questId);
            quest.setState(Quest.QuestState.IN_PROGRESS);
            LOGGER.info("Player {} started quest: {}", player.getName().getString(), questId);
        }
    }
    
    /**
     * Completes a quest for a player.
     */
    public static void completeQuest(ServerPlayerEntity player, String questId) {
        Quest quest = getQuest(questId);
        if (quest != null && quest.isComplete()) {
            PlayerQuestData data = getPlayerQuestData(player);
            data.completeQuest(questId);
            quest.setState(Quest.QuestState.COMPLETED);
            
            // Give rewards
            for (ItemStack reward : quest.getRewards()) {
                player.giveItemStack(reward.copy());
            }
            
            // Give XP
            if (quest.getXpReward() > 0) {
                player.addExperience(quest.getXpReward());
            }
            
            LOGGER.info("Player {} completed quest: {}", player.getName().getString(), questId);
        }
    }
    
    /**
     * Updates quest progress for a player.
     */
    public static void updateQuestProgress(PlayerEntity player, String objectiveType, Object data) {
        PlayerQuestData playerData = getPlayerQuestData(player);
        for (String questId : playerData.getActiveQuests()) {
            Quest quest = getQuest(questId);
            if (quest != null) {
                quest.updateProgress(player, objectiveType, data);
            }
        }
    }
    
    /**
     * Checks if a player has completed a quest.
     */
    public static boolean hasCompletedQuest(PlayerEntity player, String questId) {
        return getPlayerQuestData(player).hasCompletedQuest(questId);
    }
    
    /**
     * Registers all quests.
     */
    public static void register() {
        LOGGER.info("Registering quests...");
        // Quests will be loaded from resource files
    }
    
    /**
     * Stores quest data for a player.
     */
    public static class PlayerQuestData {
        private final Set<String> activeQuests = new HashSet<>();
        private final Set<String> completedQuests = new HashSet<>();
        
        public void startQuest(String questId) {
            activeQuests.add(questId);
        }
        
        public void completeQuest(String questId) {
            activeQuests.remove(questId);
            completedQuests.add(questId);
        }
        
        public Set<String> getActiveQuests() {
            return activeQuests;
        }
        
        public Set<String> getCompletedQuests() {
            return completedQuests;
        }
        
        public boolean hasCompletedQuest(String questId) {
            return completedQuests.contains(questId);
        }
        
        public boolean hasActiveQuest(String questId) {
            return activeQuests.contains(questId);
        }
        
        public NbtCompound toNbt() {
            NbtCompound nbt = new NbtCompound();
            
            NbtList activeList = new NbtList();
            for (String questId : activeQuests) {
                activeList.add(NbtString.of(questId));
            }
            nbt.put("ActiveQuests", activeList);
            
            NbtList completedList = new NbtList();
            for (String questId : completedQuests) {
                completedList.add(NbtString.of(questId));
            }
            nbt.put("CompletedQuests", completedList);
            
            return nbt;
        }
        
        public void fromNbt(NbtCompound nbt) {
            activeQuests.clear();
            NbtList activeList = nbt.getList("ActiveQuests", 8); // 8 = String
            for (int i = 0; i < activeList.size(); i++) {
                activeQuests.add(activeList.getString(i));
            }
            
            completedQuests.clear();
            NbtList completedList = nbt.getList("CompletedQuests", 8);
            for (int i = 0; i < completedList.size(); i++) {
                completedQuests.add(completedList.getString(i));
            }
        }
    }
}
