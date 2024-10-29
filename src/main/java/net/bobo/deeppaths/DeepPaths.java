package net.bobo.deeppaths;

import net.bobo.deeppaths.datagen.DeepPathsWorldGenerator;
import net.fabricmc.api.ModInitializer;

import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeepPaths implements ModInitializer {

	public static final String MOD_ID = "deep-paths";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {


		CustomPortalBuilder.beginPortal()
				.frameBlock(Blocks.QUARTZ_BLOCK)
				.lightWithItem(Items.STICK)
				.destDimID(new Identifier(DeepPaths.MOD_ID, "distortion_realm"))
				.tintColor(11, 3, 38)
				.flatPortal()
				.registerPortal();
	}
}