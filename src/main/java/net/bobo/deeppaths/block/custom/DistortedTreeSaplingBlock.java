package net.bobo.deeppaths.block.custom;

import net.bobo.deeppaths.block.DeepPathsBlocks;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DistortedTreeSaplingBlock extends SaplingBlock {
    public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;
    private static final EnumProperty<Direction> FACING = Properties.FACING;


    public DistortedTreeSaplingBlock(SaplingGenerator generator, Settings settings) {
        super(generator, settings);
        setDefaultState((getDefaultState().with(FACING, Direction.UP)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }



    private Direction getGrowthDirection(ServerWorld world, BlockPos pos) {
        //Check for space in growth Direction
        if (world.getBlockState(pos.up()).isOf(DeepPathsBlocks.DISTORTED_STONE)) return Direction.DOWN;
        if (world.getBlockState(pos.down()).isOf(DeepPathsBlocks.DISTORTED_STONE)) return Direction.UP;
        if (world.getBlockState(pos.north()).isOf(DeepPathsBlocks.DISTORTED_STONE)) return Direction.SOUTH;
        if (world.getBlockState(pos.south()).isOf(DeepPathsBlocks.DISTORTED_STONE)) return Direction.NORTH;
        if (world.getBlockState(pos.east()).isOf(DeepPathsBlocks.DISTORTED_STONE)) return Direction.WEST;
        if (world.getBlockState(pos.west()).isOf(DeepPathsBlocks.DISTORTED_STONE)) return Direction.EAST;

        return Direction.UP; // Default fallback
    }

    private boolean hasGrowthSpace(ServerWorld world, BlockPos pos, Direction growthDir) {
        // Check for space in growth direction
        for (int i = 1; i <= 5; i++) {
            BlockPos checkPos = pos.offset(growthDir, i);
            if (!world.getBlockState(checkPos).isAir()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        List<BlockPos> blockPos = new ArrayList<BlockPos>();
        List<BlockState> floor = new ArrayList<BlockState>();
        //block position array
        blockPos.add(pos.up());
        blockPos.add(pos.down());
        blockPos.add(pos.north());
        blockPos.add(pos.south());
        blockPos.add(pos.east());
        blockPos.add(pos.west());
        //blockstate array

        floor.add(world.getBlockState(pos.up()));
        floor.add(world.getBlockState(pos.down()));
        floor.add(world.getBlockState(pos.north()));
        floor.add(world.getBlockState(pos.south()));
        floor.add(world.getBlockState(pos.east()));
        floor.add(world.getBlockState(pos.west()));

        return canPlantOnTop(floor, world, blockPos);
    }

    //Checks if can plant on any nearby block
    protected boolean canPlantOnTop(List<BlockState> floors, BlockView world, List<BlockPos> pos) {
        int plantableBlocks = 0;
        for (BlockState floor : floors) {
            plantableBlocks += ( floor.isOf(DeepPathsBlocks.DISTORTED_STONE) ) ? 1 : 0;
            if (plantableBlocks > 1) {return false;}
        }
        return plantableBlocks == 1;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }
}
