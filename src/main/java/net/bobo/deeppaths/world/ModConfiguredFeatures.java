package net.bobo.deeppaths.world;

import com.terraformersmc.modmenu.util.mod.Mod;
import net.bobo.deeppaths.DeepPaths;
import net.bobo.deeppaths.block.DeepPathsBlocks;
import net.bobo.deeppaths.world.tree.custom.DistortedTreeFeature;
import net.bobo.deeppaths.world.tree.custom.DistortedTrunkPlacer;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> DISTORTED_TREE_KEY = registerKey("distorted_tree");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, DISTORTED_TREE_KEY, ModFeatureTypes.DISTORTED_TREE_FEATURE, new TreeFeatureConfig.Builder(
                //tall, dark oak log trees
                BlockStateProvider.of(Blocks.DARK_OAK_LOG),
                new DistortedTrunkPlacer(7,4,10),

                BlockStateProvider.of(Blocks.DARK_OAK_LEAVES),
                new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2),

                new TwoLayersFeatureSize(0, 0, 0))
                //Can grow on dirt
                .dirtProvider(BlockStateProvider.of(DeepPathsBlocks.DISTORTED_STONE))
                .build());
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(DeepPaths.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
