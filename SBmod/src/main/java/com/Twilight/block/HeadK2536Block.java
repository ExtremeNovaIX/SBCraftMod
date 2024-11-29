package com.Twilight.block;
import com.Twilight.SBMod.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ChunkTaskPriorityQueueSorter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Stack;

public class HeadK2536Block extends Block {

    public HeadK2536Block(Properties sound) {
        // Let our block behave like a metal block
        super(Properties.of()
                .strength(3.5F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.METAL)
                .randomTicks());
    }


    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.isClientSide) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() == Items.IRON_INGOT) {
                stack.shrink(1);
                ItemStack diamond = new ItemStack(Items.DIAMOND);
                player.addItem(diamond);
                player.displayClientMessage(Component.literal("送给你钻石！"), true);
            }
            if (stack.getItem() == Main.SHIT.get()) {
                stack.shrink(1);
                ItemStack diamond = new ItemStack(Items.DIAMOND);
                player.addItem(diamond);
                player.displayClientMessage(Component.literal("哇塞，皓齿皓齿"), true);
            }
        }
        return InteractionResult.PASS;

    }
}