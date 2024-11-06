package net.bobo.deeppaths;

import net.bobo.deeppaths.datagen.DeepPathsWorldGenerator;
import net.bobo.deeppaths.datagen.ModModelProvider;
import net.bobo.deeppaths.world.dimension.DistortionRealm;
import net.bobo.deeppaths.world.ModConfiguredFeatures;
import net.bobo.deeppaths.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class DeepPathsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(DeepPathsWorldGenerator::new);
		pack.addProvider(ModModelProvider::new);
	}

	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.DIMENSION_TYPE, DistortionRealm::bootstrapType);
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
	}
}
