package net.bobo.deeppaths.block.custom;

import gravity_changer.EntityTags;
import gravity_changer.GravityComponent;
import gravity_changer.api.GravityChangerAPI;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;

public class DistortedStoneBlock extends Block {
    public static final double EFFECT_RANGE = 1.0;
    public static final double PRIORITY_BASE = 1000.0;
    public static final double MIN_DISTANCE_THRESHOLD = 0.6;
    public static final double HYSTERESIS_FACTOR = 1.2; // New direction must be this much better

    public DistortedStoneBlock(Settings settings) {
        super(settings.ticksRandomly());
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // When a random tick occurs, start scheduling regular ticks if they aren't already happening
        if (!world.getBlockTickScheduler().isQueued(pos, this)) {
            world.scheduleBlockTick(pos, this, 1);
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        // Clean up scheduled ticks when block is removed
        if (state.getBlock() != newState.getBlock()) {
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isClient()) {
            checkAndApplyGravity(world, pos);
            world.scheduleBlockTick(pos, this, 1);
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if(!world.isClient()) {
            if (!world.getBlockTickScheduler().isQueued(pos, this)) {
                world.scheduleBlockTick(pos, this, 1);
            }
        }
    }


    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if(!world.isClient()) {
            world.scheduleBlockTick(pos, this, 1);
        }
    }

    public void checkAndApplyGravity(World world, BlockPos pos) {
        Box roughBox = new Box(pos).expand(EFFECT_RANGE);

        List<Entity> entities = world.getEntitiesByClass(
                Entity.class,
                roughBox,
                EntityTags::canChangeGravity
        );

        for (Entity entity : entities) {
            Vec3d entityPos = entity.getPos();
            Vec3d blockCenter = Vec3d.ofCenter(pos);
            Direction bestDirection = null;
            double closestDistance = Double.MAX_VALUE;
            double bestPriority = Double.MIN_VALUE;

            // Check each direction
            for (Direction dir : Direction.values()) {
                // Skip if this face is blocked by another solid block
                BlockPos neighborPos = pos.offset(dir);
                if (world.getBlockState(neighborPos).isFullCube(world, neighborPos)) {
                    continue;
                }

                Vec3d dirVec = Vec3d.of(dir.getVector());
                Vec3d faceCenter = blockCenter.add(dirVec.multiply(0.5));
                Vec3d deltaVec = entityPos.subtract(faceCenter);

                // Project delta onto the face normal to get distance from face
                double distanceFromFace = deltaVec.dotProduct(dirVec);

                // Get distance along the face plane
                Vec3d projectedDelta = deltaVec.subtract(dirVec.multiply(distanceFromFace));
                double distanceAlongFace = projectedDelta.length();

                // Only affect entities in front of the face and within range
                if (distanceFromFace > 0 && distanceFromFace < EFFECT_RANGE &&
                        distanceAlongFace < 1.0) { // Within block face bounds

                    double totalDistance = Math.sqrt(distanceFromFace * distanceFromFace +
                            distanceAlongFace * distanceAlongFace);

                    // Calculate priority based on distance
                    double priority = PRIORITY_BASE - totalDistance;

                    if (priority > bestPriority) {
                        bestPriority = priority;
                        bestDirection = dir;
                        closestDistance = totalDistance;
                    }
                }
            }

            // Only apply if we found a valid direction and are close enough
            if (bestDirection != null && closestDistance < MIN_DISTANCE_THRESHOLD) {
                GravityChangerAPI.setBaseGravityDirection(entity, bestDirection.getOpposite());
            }
        }
    }


}
