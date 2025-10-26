package com.corruptionmod.command;

import com.corruptionmod.event.WorldCorruptionTicker;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

/**
 * Enregistre la commande /corruption spread
 */
public class ModCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                LiteralArgumentBuilder.<ServerCommandSource>literal("corruption")
                    .then(CommandManager.literal("spread").executes(ctx -> {
                        ServerCommandSource src = ctx.getSource();
                        if (src.getWorld().getServer() == null) return 0;
                        if (src.getWorld().getRegistryKey() != null) {
                            try {
                                net.minecraft.server.world.ServerWorld sw = src.getWorld().getServer().getWorld(src.getWorld().getRegistryKey());
                                if (sw != null) {
                                    WorldCorruptionTicker.forceSpread(sw);
                                    src.sendFeedback(Text.literal("Corruption spread forced in this world."), false);
                                    return 1;
                                }
                            } catch (Exception e) {
                                // ignore
                            }
                        }
                        src.sendError(Text.literal("Could not force corruption spread."));
                        return 0;
                    }))
            );
        });
    }
}
