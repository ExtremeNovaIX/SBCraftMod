// FILEPATH: E:/SBCraft-mod/SBmod/src/main/java/com/Twilight/Packet/CustomPacket.java
package com.Twilight.Packet;

import com.Twilight.ModEntities.custom.Explosion_Sheep;
import com.Twilight.ModItems.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (packet.getMessage().startsWith("ExplosionSheepLanded:")) {
                    System.out.println("收到ExplosionSheepLanded消息: " + packet.getMessage());
                    String[] parts = packet.getMessage().split(":");
                    if (parts.length == 2) {
                        try {
                            int entityId = Integer.parseInt(parts[1]);
                            System.out.println("解析的实体ID: " + entityId);
                            Entity entity = player.level().getEntity(entityId);
                            if (entity != null) {
                                System.out.println("找到实体: " + entity.getClass().getName());
                                if (entity instanceof Explosion_Sheep) {
                                    System.out.println("实体是Explosion_Sheep，准备触发爆炸");
                                    // 触发爆炸
                                    player.level().explode(entity, entity.getX(), entity.getY(), entity.getZ(), 4.0F, Level.ExplosionInteraction.TNT);
                                    entity.remove(Entity.RemovalReason.KILLED);
                                    System.out.println("爆炸已触发，实体已移除");
                                } else {
                                    System.out.println("实体不是Explosion_Sheep");
                                }
                            } else {
                                System.out.println("未找到对应ID的实体");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("实体ID解析错误: " + parts[1]);
                        }
                    } else {
                        System.out.println("消息格式不正确: " + packet.getMessage());
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
