package com.corruptionmod.block;

import com.corruptionmod.event.CleansingRitualManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Cleansing Altar Block - center of a multi-block structure for performing cleansing rituals.
 * Right-click with required items to perform ritual and purify corrupted blocks in area.
 */
public class CleansingAltarBlock extends Block {
    
    public CleansingAltarBlock(Settings settings) {
        super(settings);
    }
    
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        
        if (world instanceof ServerWorld serverWorld) {
            // Check if the altar structure is valid
            if (!CleansingRitualManager.isValidStructure(serverWorld, pos)) {
                player.sendMessage(Text.literal("The altar structure is incomplete!"), true);
                return ActionResult.FAIL;
            }
            
            // Attempt to perform the ritual
            boolean success = CleansingRitualManager.attemptRitual(serverWorld, pos, player);
            
            if (success) {
                player.sendMessage(Text.literal("The ritual succeeds! Corruption recedes..."), false);
            } else {
                player.sendMessage(Text.literal("The ritual requires offerings..."), true);
            }
            
            return ActionResult.SUCCESS;
        }
        
        return ActionResult.PASS;
    }
    
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        // Mystical particles around the altar
        if (random.nextInt(3) == 0) {
            double x = (double)pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.5;
            double y = (double)pos.getY() + 1.0;
            double z = (double)pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.5;
            world.addParticle(ParticleTypes.ENCHANT, x, y, z, 0.0, 0.05, 0.0);
        }
    }
}
