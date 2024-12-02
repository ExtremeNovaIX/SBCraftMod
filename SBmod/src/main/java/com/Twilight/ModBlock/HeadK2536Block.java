package com.Twilight.ModBlock;

import com.Twilight.ModItems.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class HeadK2536Block extends Block {
    //金属类方块，挖掘等级3.5
    public HeadK2536Block(Properties properties) {
        super(Properties.of()
                .strength(3.5F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.METAL)
                .randomTicks());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        Style yellowStyle = Style.EMPTY.withColor(TextColor.fromRgb(0xFFFF00));//黄色字体
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {//客户端逻辑
            if (stack.getItem() == Items.IRON_INGOT) {
                stack.shrink(1);
                Component diamondMessage = Component.literal("<K2536>:送给你钻石！").setStyle(yellowStyle);
                player.displayClientMessage(diamondMessage, true);
            }
            if (stack.getItem().isEdible() && stack.getItem() != ModItems.SHIT.get()) {//判断手中物品是否可吃且不为屎
                stack.shrink(1);
                Component HaoChi = Component.literal("<K2536>:哇塞，皓齿皓齿我吃吃吃").setStyle(yellowStyle);
                player.displayClientMessage(HaoChi, true);
            } else if (stack.getItem() == ModItems.SHIT.get()) {
                Component rainbowComponent = Component.empty();
                ItemStack shit = new ItemStack(ModItems.SHIT.get(), 1);
                String text = "<K2536>:我爱吃屎芜钨吴武雾芜芜芜芜芜芜芜芜芜芜唔啊护i哦撒旦藕片aiusLLLLLLLLL";
                int[][] rainbowColors = {//彩虹色数组
                        {255, 0, 0}, // 红色
                        {255, 165, 0}, // 橙色
                        {255, 255, 0}, // 黄色
                        {0, 128, 0}, // 绿色
                        {0, 0, 255}, // 蓝色
                        {75, 0, 130}, // 青色
                        {238, 130, 238} // 紫色
                };
                for (int i = 0; i < text.length(); i++) {
                    int colorIndex = i % rainbowColors.length;
                    Style colorStyle = Style.EMPTY.withColor(TextColor.fromRgb(getColorValue(rainbowColors[colorIndex])));
                    Component part = Component.literal(text.substring(i, i + 1)).setStyle(colorStyle);
                    rainbowComponent = rainbowComponent.copy().append(part);
                }
                player.displayClientMessage(rainbowComponent, true);// 发送带有彩虹色样式的消息给玩家
            }
        }else{//服务端逻辑
            if (stack.getItem() == Items.IRON_INGOT) {
                stack.shrink(1);
                ItemStack diamond = new ItemStack(Items.DIAMOND);
                player.addItem(diamond);
            }else if (stack.getItem().isEdible() && stack.getItem() != ModItems.SHIT.get()){
                stack.shrink(1);
                level.playSound(null,pos, SoundEvents.GENERIC_EAT, SoundSource.BLOCKS,1.0F,1.0F);//播放吃东西音效
                ItemStack shit = new ItemStack(ModItems.SHIT.get(),1);
                player.addItem(shit);
            } else if (stack.getItem() == ModItems.SHIT.get()) {
                for (int i = 0; i < 10; i++) {
                    level.playSound(null,pos, SoundEvents.GENERIC_EAT, SoundSource.BLOCKS,1.0F,1.0F);
                }
                stack.shrink(1);
                //在方块上生成物品喷泉

                ItemStack itemStack = new ItemStack(ModItems.SHIT.get());
                createItemFountain(level, pos, itemStack ,0.5,1.0);
            }
        }
        return InteractionResult.PASS;
    }

public void createItemFountain(Level level, BlockPos pos, ItemStack item, double radius, double height) {
        Random random = new Random();

        int count = random.nextInt(63) + 1;
        // 使喷泉在方块正上方生成
        Vec3 fountainPos = new Vec3(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);

        for (int i = 0; i < count; i++) {
            // 计算随机位置
            double x = fountainPos.x + (random.nextDouble() - 0.5) * radius * 2;
            double y = fountainPos.y + height * random.nextDouble(); // 随机高度，最高不超过设定高度
            double z = fountainPos.z + (random.nextDouble() - 0.5) * radius * 2;

            // 创建物品实体
            ItemEntity itemEntity = new ItemEntity(level, x, y, z, item.copy());

            // 设置物品的运动和范围
            itemEntity.setDeltaMovement(
                    (random.nextDouble() - 0.5) * 0.2, // 减小水平速度
                    random.nextDouble() * 0.4, // 减小垂直速度
                    (random.nextDouble() - 0.5) * 0.2  // 减小水平速度
            );

            // 将物品添加到世界中
            level.addFreshEntity(itemEntity);
        }
    }


    private void spawnItemFountain(Level level, BlockPos pos) {
        ItemStack itemToSpawn = new ItemStack(ModItems.SHIT.get()); // 使用已注册的SHIT物品
        createItemFountain(level, pos, itemToSpawn, 0.5, 2.0);
    }
    // 将RGB颜色数组转换为十六进制颜色值
    private static int getColorValue(int[] rgb) {
        return ((rgb[0] & 0xff) << 16) | ((rgb[1] & 0xff) << 8) | (rgb[2] & 0xff);
    }
}
