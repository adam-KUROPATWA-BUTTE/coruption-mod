package com.corruptionmod.util;

import net.minecraft.entity.player.PlayerEntity;
import java.util.Random;

/**
 * Gère les dialogues aléatoires de l'Étranger.
 */
public class DialogueManager {
    private static final String[] DIALOGUES = {
        "§5[Étranger] §7Le Néant s'éveille...",
        "§5[Étranger] §7Vous sentez cette présence ?",
        "§5[Étranger] §7Tout va bientôt sombrer..."
    };
    
    private static final String[] STRANGER_DIALOGUES = {
        "§5[Étranger] §7La corruption grandit... Méfiez-vous...",
        "§5[Étranger] §7J'ai vu des choses terribles dans le Néant...",
        "§5[Étranger] §7Vous cherchez à purifier ce monde ? C'est noble... mais vain.",
        "§5[Étranger] §7Le Harbinger attend... Il vous observe déjà.",
        "§5[Étranger] §7Dans le Néant, le temps n'a plus de sens..."
    };

    public static String getRandomDialogue() {
        return DIALOGUES[new Random().nextInt(DIALOGUES.length)];
    }
    
    public static String getStrangerDialogue(int index, PlayerEntity player) {
        int actualIndex = index % STRANGER_DIALOGUES.length;
        String dialogue = STRANGER_DIALOGUES[actualIndex];
        // Add player name personalization if desired
        return dialogue;
    }
}
