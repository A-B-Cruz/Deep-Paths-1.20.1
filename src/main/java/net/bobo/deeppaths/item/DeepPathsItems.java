package net.bobo.deeppaths.item;

import net.bobo.deeppaths.DeepPaths;
import net.bobo.deeppaths.block.DeepPathsBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.impl.itemgroup.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class DeepPathsItems {

    private static void addItemsToIngredientTabItemGroups(FabricItemGroupEntries entries) {

    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(DeepPaths.MOD_ID, name), item);
    }

    public static void registerDeepPathsItems() {
        DeepPaths.LOGGER.info("Registering Mod Items for " + DeepPaths.MOD_ID);

        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(DeepPathsItems::addItemsToIngredientTabItemGroups);
    }
}
