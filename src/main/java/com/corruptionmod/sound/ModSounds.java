package com.corruptionmod.sound;

import com.corruptionmod.CorruptionMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

/**
 * Registry for all custom sound events in the Corruption Mod.
 */
public class ModSounds {
    
    // Ambient Sounds
    public static final SoundEvent AMBIENT_VOID_THEME = registerSound("ambient.void_theme");
    public static final SoundEvent AMBIENT_CORRUPTION_WHISPERS = registerSound("ambient.corruption_whispers");
    public static final SoundEvent AMBIENT_CORRUPTION_HEARTBEAT = registerSound("ambient.corruption_heartbeat");
    
    // Block Sounds
    public static final SoundEvent BLOCK_CORRUPTION_SPREAD = registerSound("block.corruption.spread");
    public static final SoundEvent BLOCK_CORRUPTION_BREAK = registerSound("block.corruption.break");
    public static final SoundEvent BLOCK_CORRUPTION_PLACE = registerSound("block.corruption.place");
    public static final SoundEvent BLOCK_CORRUPTION_STEP = registerSound("block.corruption.step");
    public static final SoundEvent BLOCK_PURIFICATION_ACTIVATE = registerSound("block.purification.activate");
    public static final SoundEvent BLOCK_CRYSTAL_CHIME = registerSound("block.crystal.chime");
    
    // Entity Sounds
    public static final SoundEvent ENTITY_CORRUPTED_HURT = registerSound("entity.corrupted.hurt");
    public static final SoundEvent ENTITY_CORRUPTED_DEATH = registerSound("entity.corrupted.death");
    public static final SoundEvent ENTITY_CORRUPTED_AMBIENT = registerSound("entity.corrupted.ambient");
    public static final SoundEvent ENTITY_BOSS_ROAR = registerSound("entity.boss.roar");
    public static final SoundEvent ENTITY_BOSS_ATTACK = registerSound("entity.boss.attack");
    
    // Portal Sounds
    public static final SoundEvent PORTAL_ACTIVATE = registerSound("portal.activate");
    public static final SoundEvent PORTAL_TRAVEL = registerSound("portal.travel");
    
    // Music
    public static final SoundEvent MUSIC_VOID_REALM = registerSound("music.void_realm");
    public static final SoundEvent MUSIC_BOSS_FIGHT = registerSound("music.boss_fight");
    
    /**
     * Register a sound event with the given path
     */
    private static SoundEvent registerSound(String path) {
        Identifier id = new Identifier(CorruptionMod.MOD_ID, path);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
    
    /**
     * Initialize sound registry - called from main mod class
     */
    public static void register() {
        CorruptionMod.LOGGER.info("Registering Corruption Mod sounds");
        // Sound events are registered via static initialization
    }
}
