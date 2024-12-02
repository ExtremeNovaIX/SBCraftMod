package com.Twilight.Event;

import com.Twilight.Client.KeyBindings;
import com.Twilight.ModBlock.HeadK2536Block;
import com.Twilight.ModBlock.ModBLock;
import com.Twilight.ModItems.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.Level;

@Mod.EventBusSubscriber(modid = "sbmod", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ShitKey {

    private static final Logger log = LoggerFactory.getLogger(ShitKey.class);

    @SubscribeEvent
    public static void onKeyInput(TickEvent.ServerTickEvent event) {


        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null || !mc.isWindowActive()) return;

        if (KeyBindings.INSTANCE.SHIT_KEY.consumeClick()) {
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            if (mc.level.isClientSide()) {
                player.displayClientMessage(player.getDisplayName().copy().append(" dropped some shit"), false);

            }else{
                ItemStack shitStack = new ItemStack(ModItems.SHIT.get(), 1);
                for (int i = 0; i < 10; i++) {
                    ItemEntity shitEntity = new ItemEntity(mc.level, x, y, z, shitStack);
                    mc.level.addFreshEntity(shitEntity);
                }
            }
        }
    }
}
