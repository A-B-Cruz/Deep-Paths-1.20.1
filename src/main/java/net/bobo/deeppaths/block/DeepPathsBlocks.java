package net.bobo.deeppaths.block;

import net.bobo.deeppaths.DeepPaths;
import net.bobo.deeppaths.block.custom.DistortedStoneBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class DeepPathsBlocks {

    public static final Block DISTORTED_STONE = registerBlock("distorted_stone",
           new DistortedStoneBlock(FabricBlockSettings.copyOf(Blocks.TERRACOTTA)));


    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(DeepPaths.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(DeepPaths.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        DeepPaths.LOGGER.info("Registering blocks for " + DeepPaths.MOD_ID);
    }

}
