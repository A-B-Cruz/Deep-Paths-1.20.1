package net.bobo.deeppaths;

import net.bobo.deeppaths.block.DeepPathsBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class DeepPathsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(DeepPathsBlocks.DISTORTED_SAPLING, RenderLayer.getCutout());
    }
}
