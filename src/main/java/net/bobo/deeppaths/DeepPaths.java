package net.bobo.deeppaths;


import gravity_changer.GravityChangerMod;
import gravity_changer.api.GravityChangerAPI;
import gravity_changer.item.GravityChangerItemAOE;
import net.bobo.deeppaths.block.DeepPathsBlocks;
import net.bobo.deeppaths.datagen.DeepPathsWorldGenerator;
import net.bobo.deeppaths.item.DeepPathsItemGroups;
import net.bobo.deeppaths.item.DeepPathsItems;
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

		DeepPathsBlocks.registerModBlocks();
		DeepPathsItemGroups.registerItemGroups();
	}

}