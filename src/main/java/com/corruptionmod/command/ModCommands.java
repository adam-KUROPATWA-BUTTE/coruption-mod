package com.corruptionmod.command;

import com.corruptionmod.event.WorldCorruptionTicker;
import com.corruptionmod.util.PerformanceMonitor;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

/**
 * Registers corruption-related commands
 */
public class ModCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                LiteralArgumentBuilder.<ServerCommandSource>literal("corruption")
                    // /corruption spread - Force corruption spread
                    .then(CommandManager.literal("spread").executes(ctx -> {
                        ServerCommandSource src = ctx.getSource();
                        if (src.getWorld().getServer() == null) return 0;
                        if (src.getWorld().getRegistryKey() != null) {
                            try {
                                net.minecraft.server.world.ServerWorld sw = src.getWorld().getServer().getWorld(src.getWorld().getRegistryKey());
                                if (sw != null) {
                                    WorldCorruptionTicker.forceSpread(sw);
                                    src.sendFeedback(() -> Text.literal("Corruption spread forced in this world."), false);
                                    return 1;
                                }
                            } catch (Exception e) {
                                // ignore
                            }
                        }
                        src.sendError(Text.literal("Could not force corruption spread."));
                        return 0;
                    }))
                    
                    // /corruption stats - Show corruption statistics
                    .then(CommandManager.literal("stats").executes(ctx -> {
                        ServerCommandSource src = ctx.getSource();
                        
                        // Update memory stats
                        PerformanceMonitor.updateMemoryStats();
                        
                        // Send statistics
                        src.sendFeedback(() -> Text.literal("§6=== Corruption Mod Statistics ==="), false);
                        src.sendFeedback(() -> Text.literal("§eCorruption Data:"), false);
                        src.sendFeedback(() -> Text.literal("  Active Corrupted Chunks: §f" + PerformanceMonitor.getActiveCorruptedChunks()), false);
                        src.sendFeedback(() -> Text.literal("  Total Corrupted Blocks: §f" + PerformanceMonitor.getTotalCorruptedBlocks()), false);
                        src.sendFeedback(() -> Text.literal("  Active Corrupted Entities: §f" + PerformanceMonitor.getActiveCorruptedEntities()), false);
                        src.sendFeedback(() -> Text.literal("  Corruption Spread Rate: §f" + String.format("%.1f", PerformanceMonitor.getCorruptionSpreadRate()) + " blocks/tick"), false);
                        
                        src.sendFeedback(() -> Text.literal("§ePerformance:"), false);
                        src.sendFeedback(() -> Text.literal("  Average Tick Time: §f" + String.format("%.3f", PerformanceMonitor.getAverageTickTimeMs()) + " ms"), false);
                        src.sendFeedback(() -> Text.literal("  Peak Tick Time: §f" + String.format("%.3f", PerformanceMonitor.getPeakTickTimeMs()) + " ms"), false);
                        
                        src.sendFeedback(() -> Text.literal("§eMemory Usage:"), false);
                        src.sendFeedback(() -> Text.literal("  Used Memory: §f" + String.format("%.1f", PerformanceMonitor.getUsedMemoryMB()) + " MB"), false);
                        src.sendFeedback(() -> Text.literal("  Max Memory: §f" + String.format("%.1f", PerformanceMonitor.getMaxMemoryMB()) + " MB"), false);
                        src.sendFeedback(() -> Text.literal("  Usage: §f" + String.format("%.1f", PerformanceMonitor.getMemoryUsagePercent()) + "%"), false);
                        
                        return 1;
                    }))
                    
                    // /corruption reset - Reset statistics
                    .then(CommandManager.literal("reset").requires(src -> src.hasPermissionLevel(2)).executes(ctx -> {
                        ServerCommandSource src = ctx.getSource();
                        PerformanceMonitor.reset();
                        src.sendFeedback(() -> Text.literal("§aCorruption statistics reset."), false);
                        return 1;
                    }))
            );
        });
    }
}
