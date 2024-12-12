// FILEPATH: E:/SBCraft-mod/SBmod/src/main/java/com/Twilight/Packet/CustomPacket.java
package com.Twilight.Packet;

import com.Twilight.ModItems.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import java.util.Random;

import java.util.function.Supplier;

public class CustomPacket {
    private final String message;

    public CustomPacket(String message) {
        this.message = message;
    }

    public static void encode(CustomPacket packet, FriendlyByteBuf buffer) {
        buffer.writeUtf(packet.message);
    }

    public static CustomPacket decode(FriendlyByteBuf buffer) {
        return new CustomPacket(buffer.readUtf());
    }

    public static void handle(CustomPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                if (packet.getMessage().equals("ShitKey被按下！")) {
                    // 在服务端生成物品的逻辑
                    for (int i = 0; i < 9; i++) {
                    double x = player.getX();
                    double y = player.getY() + 2;
                    double z = player.getZ();
                    Random random = new Random();
                    ItemStack shitStack = new ItemStack(ModItems.SHIT.get(), 1);
                    ItemEntity itemEntity = new ItemEntity(player.level(), x, y, z, shitStack);
                    itemEntity.setDeltaMovement(
                            (random.nextDouble() - 0.5) * 0.3, // 增加水平速度
                            random.nextDouble() * 0.6, // 增加垂直速度
                            (random.nextDouble() - 0.5) * 0.3  // 增加水平速度
                    );
                        itemEntity.setPickUpDelay(20); // 设置物品可被捡起的延迟，单位为游戏刻
                        // 将物品添加到世界中
                        player.level().addFreshEntity(itemEntity);
                        try {
                            Thread.sleep(33);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    
                }
            }
        });
        context.setPacketHandled(true);
    }

    public String getMessage() {
        return message;
    }
}
