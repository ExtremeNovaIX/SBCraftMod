package com.Twilight.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static com.Twilight.SBMod.Main.HEAD_K2536_BLOCK;

public class Shit extends Item {

    public Shit(Properties p_41383_) {
        super(p_41383_);
    }
    public InteractionResult useOn(UseOnContext p_186371_) {
        Level level = p_186371_.getLevel();
        BlockPos blockpos = p_186371_.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        Block block = blockstate.getBlock();

        if (block == HEAD_K2536_BLOCK.get()){
            Player player = p_186371_.getPlayer();
            player.addItem(new ItemStack(Items.DIAMOND));
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        return super.useOn(p_186371_);
    }
}
