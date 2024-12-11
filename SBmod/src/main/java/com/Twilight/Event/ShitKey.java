package com.Twilight.Event;

import com.Twilight.Client.KeyBindings;
import com.Twilight.Packet.CustomPacket;
import com.Twilight.SBMod.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "sbmod", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ShitKey {
    // 设置发包间隔以防服务器卡成傻逼
    private static final long PACKET_INTERVAL = 300;
    private static long lastPacketTime = 0;

    @SubscribeEvent
    public static void onKeyInput(PlayerEvent event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null || !mc.isWindowActive()) return;

        if (KeyBindings.INSTANCE.SHIT_KEY.isDown()) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastPacketTime >= PACKET_INTERVAL) {
                // 发送数据包到服务端
                Main.PACKET_HANDLER.sendToServer(new CustomPacket("ShitKey被按下！"));
                lastPacketTime = currentTime;
            }
        }
    }
}
