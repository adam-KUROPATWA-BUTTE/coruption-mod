package com.corruptionmod.util;

import java.util.Random;

/**
 * Gère les dialogues aléatoires de l'Étranger.
 */
public class DialogueManager {
    private static final String[] DIALOGUES = {
        "§5[Étranger] §7Le Néant s’éveille...",
        "§5[Étranger] §7Vous sentez cette présence ?",
        "§5[Étranger] §7Tout va bientôt sombrer..."
    };

    public static String getRandomDialogue() {
        return DIALOGUES[new Random().nextInt(DIALOGUES.length)];
    }
}
