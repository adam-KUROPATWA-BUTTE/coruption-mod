package com.corruptionmod.item;

import com.corruptionmod.ModDimensions;
import com.corruptionmod.block.VoidPortalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Collections;

/**
 * Item used to activate void portals and teleport to/from the Void Realm.
 */
public class VoidKeyItem extends Item {
    public VoidKeyItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        
        if (!world.isClient && world instanceof ServerWorld serverWorld) {
            ServerWorld targetWorld;
            Vec3d targetPos;
            
            // Teleport between overworld and void realm
            if (world.getRegistryKey() == World.OVERWORLD) {
                targetWorld = ModDimensions.getVoidRealmWorld(serverWorld.getServer());
                if (targetWorld == null) {
                    player.sendMessage(Text.literal("Void Realm dimension not available"), false);
                    return TypedActionResult.fail(stack);
                }
                targetPos = new Vec3d(player.getX(), 64, player.getZ());
            } else if (world.getRegistryKey() == ModDimensions.LE_NEANT_WORLD_KEY) {
                targetWorld = serverWorld.getServer().getOverworld();
                targetPos = new Vec3d(player.getX(), player.getY(), player.getZ());
            } else {
                return TypedActionResult.fail(stack);
            }
            
            // Use vanilla teleport API (1.21.1)
            player.teleport(targetWorld, targetPos.getX(), targetPos.getY(), targetPos.getZ(), 
                Collections.emptySet(), player.getYaw(), player.getPitch());
            
            // Play sound effects
            world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_PORTAL_TRAVEL, 
                           SoundCategory.PLAYERS, 1.0f, 1.0f);
            
            // Damage the item
            if (!player.isCreative()) {
                stack.damage(1, player, LivingEntity.getSlotForHand(hand));
            }
            
            return TypedActionResult.success(stack);
        }
        
        return TypedActionResult.pass(stack);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        
        // Check if used on portal frame
        if (world.getBlockState(pos).getBlock() instanceof VoidPortalBlock) {
            if (!world.isClient) {
                // Activate portal logic would go here
                world.playSound(null, pos, SoundEvents.BLOCK_PORTAL_TRIGGER, 
                               SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
            return ActionResult.SUCCESS;
        }
        
        return ActionResult.PASS;
    }
}
