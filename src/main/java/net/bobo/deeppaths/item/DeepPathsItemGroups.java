package net.bobo.deeppaths.item;

import net.bobo.deeppaths.DeepPaths;
import net.bobo.deeppaths.block.DeepPathsBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DeepPathsItemGroups {

    public static final ItemGroup DISTORTION_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(DeepPaths.MOD_ID, "distortion"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.distortion"))
                    .icon(() -> new ItemStack(DeepPathsBlocks.DISTORTED_STONE)).entries((displayContext, entries) -> {
                        entries.add(DeepPathsBlocks.DISTORTED_STONE);
            }).build());

    public static void registerItemGroups() {
        DeepPaths.LOGGER.info("Registering Item Groups for " + DeepPaths.MOD_ID);


    }
}
