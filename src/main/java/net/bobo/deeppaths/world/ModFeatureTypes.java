package net.bobo.deeppaths.world;

import net.bobo.deeppaths.DeepPaths;
import net.bobo.deeppaths.world.tree.custom.DistortedTreeFeature;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class ModFeatureTypes {
    public static final Feature<TreeFeatureConfig> DISTORTED_TREE_FEATURE = Registry.register(Registries.FEATURE,
            new Identifier(DeepPaths.MOD_ID, "distorted_tree_feature"),
            new DistortedTreeFeature(TreeFeatureConfig.CODEC)
    );

    public static void register() {
        DeepPaths.LOGGER.info("Registering Feature Types for " + DeepPaths.MOD_ID);
    }
}
