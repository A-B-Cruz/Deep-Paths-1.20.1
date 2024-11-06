package net.bobo.deeppaths.world.tree;

import net.bobo.deeppaths.DeepPaths;
import net.bobo.deeppaths.mixin.TrunkPlacerTypeInvoker;
import net.bobo.deeppaths.world.tree.custom.DistortedTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModTrunkPlacerTypes {
    public static final TrunkPlacerType<?> DISTORTED_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("distorted_trunk_placer", DistortedTrunkPlacer.CODEC);

    public static void register() {
        DeepPaths.LOGGER.info("Registering Trunk Placers for " + DeepPaths.MOD_ID);
    }
}
