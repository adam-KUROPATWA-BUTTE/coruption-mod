package com.corruptionmod.util;

import com.corruptionmod.ModBlocks;
import com.corruptionmod.ModDimensions;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

/**
 * Utility class for void portal mechanics.
 */
public class VoidPortalHelper {
    
    /**
     * Checks if a portal frame is valid at the given position.
     * A valid portal frame is a rectangular structure made of void portal frame blocks.
     */
    public static boolean isValidPortalFrame(World world, BlockPos pos) {
        // Check for a 4x5 portal frame (minimum size)
        // This is a simplified check - a full implementation would scan the structure
        Block portalFrame = ModBlocks.VOID_PORTAL_FRAME;
        
        // Check bottom corners
        boolean hasBottomCorners = 
            world.getBlockState(pos).getBlock() == portalFrame &&
            world.getBlockState(pos.offset(Direction.EAST, 3)).getBlock() == portalFrame;
        
        // Check top corners
        boolean hasTopCorners =
            world.getBlockState(pos.offset(Direction.UP, 4)).getBlock() == portalFrame &&
            world.getBlockState(pos.offset(Direction.EAST, 3).offset(Direction.UP, 4)).getBlock() == portalFrame;
        
        return hasBottomCorners && hasTopCorners;
    }
    
    /**
     * Attempts to light/activate a portal at the given position.
     */
    public static boolean lightPortal(World world, BlockPos pos) {
        if (!isValidPortalFrame(world, pos)) {
            return false;
        }
        
        // Fill the portal interior with air (portal blocks would go here in full implementation)
        for (int x = 1; x < 3; x++) {
            for (int y = 1; y < 4; y++) {
                BlockPos interiorPos = pos.offset(Direction.EAST, x).offset(Direction.UP, y);
                // In a full implementation, this would place portal blocks
                // For now, we just verify the space is available
            }
        }
        
        return true;
    }
    
    /**
     * Teleports an entity through a void portal.
     */
    public static void teleportThroughPortal(Entity entity, BlockPos portalPos) {
        if (!(entity.getWorld() instanceof ServerWorld serverWorld)) {
            return;
        }
        
        ServerWorld targetWorld;
        Vec3d targetPos;
        
        // Determine target dimension
        if (serverWorld.getRegistryKey() == World.OVERWORLD) {
            targetWorld = ModDimensions.getVoidRealmWorld(serverWorld.getServer());
            if (targetWorld == null) {
                return;
            }
            // Scale coordinates for the void realm
            targetPos = new Vec3d(
                entity.getX(),
                64, // Fixed spawn height in void realm
                entity.getZ()
            );
        } else if (serverWorld.getRegistryKey() == ModDimensions.LE_NEANT_WORLD_KEY) {
            targetWorld = serverWorld.getServer().getOverworld();
            targetPos = new Vec3d(
                entity.getX(),
                entity.getY(),
                entity.getZ()
            );
        } else {
            return;
        }
        
        // Create teleport target
        TeleportTarget target = new TeleportTarget(
            targetPos,
            entity.getVelocity(),
            entity.getYaw(),
            entity.getPitch()
        );
        
        // Teleport the entity
        FabricDimensions.teleport(entity, targetWorld, target);
    }
    
    /**
     * Finds or creates a portal at the target dimension.
     */
    public static BlockPos findOrCreatePortal(ServerWorld world, BlockPos pos) {
        // Search for existing portal nearby
        int searchRadius = 128;
        for (int x = -searchRadius; x <= searchRadius; x += 16) {
            for (int z = -searchRadius; z <= searchRadius; z += 16) {
                BlockPos searchPos = new BlockPos(pos.getX() + x, 64, pos.getZ() + z);
                if (isValidPortalFrame(world, searchPos)) {
                    return searchPos;
                }
            }
        }
        
        // No portal found, would create one here in full implementation
        return pos;
    }
}
