package net.bobo.deeppaths.block.custom;

import gravity_changer.EntityTags;
import gravity_changer.api.GravityChangerAPI;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class DistortedStoneBlock extends Block {

    public DistortedStoneBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                              Hand hand, BlockHitResult hit) {
        if (!world.isClient && EntityTags.canChangeGravity(player)) {
            // Get the clicked face
            Direction clickedFace = hit.getSide();
            // Change gravity to the opposite direction of the clicked face
            // (since gravity pulls towards that face)
            Direction gravityDirection = clickedFace.getOpposite();

            GravityChangerAPI.setBaseGravityDirection(player, gravityDirection);

            return ActionResult.SUCCESS;
        }

        return ActionResult.CONSUME;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }



}