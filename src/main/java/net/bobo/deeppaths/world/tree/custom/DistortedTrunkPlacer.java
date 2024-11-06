package net.bobo.deeppaths.world.tree.custom;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.bobo.deeppaths.block.DeepPathsBlocks;
import net.bobo.deeppaths.world.tree.ModTrunkPlacerTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class DistortedTrunkPlacer extends TrunkPlacer {
    public static final Codec<DistortedTrunkPlacer> CODEC = RecordCodecBuilder.create(objectInstance ->
            fillTrunkPlacerFields(objectInstance).apply(objectInstance, DistortedTrunkPlacer::new));

    public DistortedTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModTrunkPlacerTypes.DISTORTED_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {

        // Determine growth direction based on the block it's planted on
        Direction growthDirection = determineGrowthDirection(world, startPos);

        // Place base block in the appropriate position
        setToDirt(world, replacer, random, startPos.offset(growthDirection.getOpposite()), config);

        // Get the default log state from config and set its axis based on growth direction
        BlockState logState = config.trunkProvider.get(random, startPos);
        logState = logState.with(PillarBlock.AXIS, growthDirection.getAxis());

        // Generate the trunk in the determined direction
        for (int i = 0; i < height; i++) {
            BlockPos pos = startPos.offset(growthDirection, i);
            replacer.accept(pos, logState);
        }

        // Create a foliage node at the end of the trunk
        BlockPos endPos = startPos.offset(growthDirection, height);
        return ImmutableList.of(new FoliagePlacer.TreeNode(endPos, 0, false));
    }

    private Direction determineGrowthDirection(TestableWorld world, BlockPos pos) {
        // Check the block below (default case)
        if (world.testBlockState(pos.down(), state -> state.isOf(DeepPathsBlocks.DISTORTED_STONE))) {
            return Direction.UP;
        }

        // Check the block above
        if (world.testBlockState(pos.up(), state -> state.isOf(DeepPathsBlocks.DISTORTED_STONE))) {
            return Direction.DOWN;
        }

        // Check all horizontal directions
        for (Direction dir : Direction.Type.HORIZONTAL) {
            if (world.testBlockState(pos.offset(dir.getOpposite()),
                    state -> state.isOf(DeepPathsBlocks.DISTORTED_STONE))) {
                return dir;
            }
        }

        // Default to UP if no valid direction is found
        return Direction.UP;
    }

}
