// FILEPATH: E:/SBCraft-mod/SBmod/src/main/java/com/Twilight/Packet/CustomPacket.java
package com.Twilight.Packet;

import com.Twilight.ModItems.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

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
            // 在服务器端处理接收到的消息
            ServerPlayer player = context.getSender();
            if (player != null && packet.getMessage().equals("ShitKey被按下！")) {
                // 在服务端生成物品的逻辑
                double x = player.getX();
                double y = player.getY() + 2;
                double z = player.getZ();
                ItemStack shitStack = new ItemStack(ModItems.SHIT.get(), 1);
                ItemEntity itemEntity = new ItemEntity(player.level(), x, y, z, shitStack);
                player.level().addFreshEntity(itemEntity);
            }
        });
        context.setPacketHandled(true);
    }

    // 获取消息内容的方法
    public String getMessage() {
        return message;
    }
}
