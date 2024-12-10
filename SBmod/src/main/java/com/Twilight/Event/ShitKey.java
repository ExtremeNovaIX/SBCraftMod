// FILEPATH: E:/SBCraft-mod/SBmod/src/main/java/com/Twilight/Event/ShitKey.java
package com.Twilight.Event;

import com.Twilight.Client.KeyBindings;
import com.Twilight.Packet.CustomPacket;
import com.Twilight.SBMod.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "sbmod", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ShitKey {

    @SubscribeEvent
    public static void onKeyInput(TickEvent.PlayerTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null || !mc.isWindowActive()) return;

        if (KeyBindings.INSTANCE.SHIT_KEY.isDown()) {
            // 发送数据包到服务端
            Main.PACKET_HANDLER.sendToServer(new CustomPacket("ShitKey被按下！"));
        }
    }
}