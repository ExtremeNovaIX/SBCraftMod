package com.Twilight.block;

import com.Twilight.SBMod.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;
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
        Style yellowStyle = Style.EMPTY.withColor(TextColor.fromRgb(0xFFFF00));//黄色字体
        int[][] rainbowColors = {//彩虹色数组
                {255, 0, 0}, // 红色
                {255, 165, 0}, // 橙色
                {255, 255, 0}, // 黄色
                {0, 128, 0}, // 绿色
                {0, 0, 255}, // 蓝色
                {75, 0, 130}, // 青色
                {238, 130, 238} // 紫色
        };
        if (level.isClientSide) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() == Items.IRON_INGOT) {
                stack.shrink(1);
                ItemStack diamond = new ItemStack(Items.DIAMOND);
                player.addItem(diamond);
                Component diamondMessage = Component.literal("<K2536>:送给你钻石！").setStyle(yellowStyle);
                player.displayClientMessage(diamondMessage, true);
            }
            if (stack.getItem().isEdible() && stack.getItem() != Main.SHIT.get()) {//判断手中物品是否可吃
                stack.shrink(1);
                ItemStack shit = new ItemStack(Main.SHIT.get(),1);
                player.addItem(shit);
                Component HaoChi = Component.literal("<K2536>:哇塞，皓齿皓齿我吃吃吃").setStyle(yellowStyle);
                player.displayClientMessage(HaoChi, true);
            } else if (stack.getItem() == Main.SHIT.get()) {
                stack.shrink(1);
                Component rainbowComponent = Component.empty();
                ItemStack shit = new ItemStack(Main.SHIT.get(), 1);
                String text = "<K2536>:我爱吃屎芜钨吴武雾芜芜芜芜芜芜芜芜芜芜芜芜芜芜芜芜芜芜芜芜芜芜唔啊护i哦撒旦藕片aiusLLLLLLLLL";
                spawnItemFountain(level , pos , player);
                for (int i = 0; i < text.length(); i++) {
                    int colorIndex = i % rainbowColors.length;
                    Style colorStyle = Style.EMPTY.withColor(TextColor.fromRgb(getColorValue(rainbowColors[colorIndex])));
                    Component part = Component.literal(text.substring(i, i + 1)).setStyle(colorStyle);
                    rainbowComponent = rainbowComponent.copy().append(part);
                }
                player.displayClientMessage(rainbowComponent, true);// 发送带有彩虹色样式的消息给玩家
            }
        }
        return InteractionResult.PASS;
    }
    // 将RGB颜色数组转换为十六进制颜色值
    private static int getColorValue(int[] rgb) {
        return ((rgb[0] & 0xff) << 16) | ((rgb[1] & 0xff) << 8) | (rgb[2] & 0xff);
    }
    private void spawnItemFountain(Level level1, BlockPos pos1, Player player) {
        Random random = new Random();
        int itemCount = 20; // 使用随机数决定物品数量

        for (int i = 0; i < itemCount; i++) {


            ItemEntity itemEntity = new ItemEntity(level1, pos1.getX(), pos1.getY(), pos1.getZ(), new ItemStack(Items.DIAMOND));

            level1.addFreshEntity(itemEntity); // 将物品实体添加到世界
        }
    }
}