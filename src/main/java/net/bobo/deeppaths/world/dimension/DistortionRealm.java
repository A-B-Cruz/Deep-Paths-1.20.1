package net.bobo.deeppaths.world.dimension;

import net.bobo.deeppaths.DeepPaths;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;


import java.util.OptionalLong;

public class DistortionRealm {

    public static final RegistryKey<DimensionType> DISTORTION_DIMENSION_TYPE = RegistryKey.of(
            RegistryKeys.DIMENSION_TYPE, new Identifier(DeepPaths.MOD_ID, "distortion_type"));
    public static final RegistryKey<World> DISTORTION_LEVEL_KEY = RegistryKey.of(
            RegistryKeys.WORLD, new Identifier(DeepPaths.MOD_ID, "distortion_realm"));
    public static final RegistryKey<DimensionOptions> DISTORTION_DIMENSION_KEY = RegistryKey.of(
            RegistryKeys.DIMENSION, new Identifier(DeepPaths.MOD_ID, "distortion_realm"));

    public static void bootstrapType(Registerable<DimensionType> context) {
        context.register(DISTORTION_DIMENSION_TYPE, new DimensionType(
                OptionalLong.empty(), // Fixed time
                false,  // skylight
                true, // has ceiling
                false, // ultrawarm
                false,  // natural
                1.0D,  // coordinate scale
                false,  // has skylight
                false, // has raids
                -64,   // min y
                384,   // height
                384,   // logical height
                BlockTags.INFINIBURN_OVERWORLD,
                new Identifier(DeepPaths.MOD_ID, "distortion_realm"),
                0.0f,  // ambient light
                new DimensionType.MonsterSettings(false, false, UniformIntProvider.create(0, 7), 0)));
    }
}
