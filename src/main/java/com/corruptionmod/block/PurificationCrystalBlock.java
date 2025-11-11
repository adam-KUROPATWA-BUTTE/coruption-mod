package com.corruptionmod.block;

import com.corruptionmod.event.PurificationManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/**
 * Crystal de purification : quand placé, enregistre une zone purifiée autour de lui.
 */
public class PurificationCrystalBlock extends Block {
    public PurificationCrystalBlock() {
        super(AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).strength(1.5f).requiresTool());
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, net.minecraft.entity.LivingEntity placer, net.minecraft.item.ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient && world instanceof ServerWorld sw) {
            PurificationManager.addCrystal(sw, pos);
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, net.minecraft.entity.player.PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if (!world.isClient && world instanceof ServerWorld sw) {
            PurificationManager.removeCrystal(sw, pos);
        }
    }
}
