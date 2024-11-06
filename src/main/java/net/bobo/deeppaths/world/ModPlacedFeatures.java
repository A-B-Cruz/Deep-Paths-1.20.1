package net.bobo.deeppaths.world;

import net.bobo.deeppaths.DeepPaths;
import net.bobo.deeppaths.block.DeepPathsBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final RegistryKey<PlacedFeature> DISTORTED_TREE_KEY = registerKey("distorted_tree_placed");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, DISTORTED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.DISTORTED_TREE_KEY),
                List.of(
                        PlacedFeatures.createCountExtraModifier(2, 0.1f, 3),
                        SquarePlacementModifier.of(),
                        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, // Different heightmap
                        BiomePlacementModifier.of(),
                        BlockFilterPlacementModifier.of(
                                BlockPredicate.anyOf(
                                        BlockPredicate.matchingBlocks(new Vec3i(0, 1, 0), DeepPathsBlocks.DISTORTED_STONE),
                                        BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0), DeepPathsBlocks.DISTORTED_STONE),
                                        BlockPredicate.matchingBlocks(new Vec3i(1, 0, 0), DeepPathsBlocks.DISTORTED_STONE),
                                        BlockPredicate.matchingBlocks(new Vec3i(-1, 0, 0), DeepPathsBlocks.DISTORTED_STONE),
                                        BlockPredicate.matchingBlocks(new Vec3i(0, 0, 1), DeepPathsBlocks.DISTORTED_STONE),
                                        BlockPredicate.matchingBlocks(new Vec3i(0, 0, -1), DeepPathsBlocks.DISTORTED_STONE)
                                )
                        )
                ));

    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(DeepPaths.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

}
