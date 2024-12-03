package com.Twilight.Event;

import com.Twilight.Client.KeyBindings;
import com.Twilight.ModItems.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

@Mod.EventBusSubscriber(modid = "sbmod", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ShitKey {

    private static final Logger log = LoggerFactory.getLogger(ShitKey.class);

    @SubscribeEvent
    public static void onKeyInput(LevelEvent event) {


        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null || !mc.isWindowActive()) return;
        if (KeyBindings.INSTANCE.SHIT_KEY.isDown()) {

            double x = player.getX();
            double y = player.getY() + 2;
            double z = player.getZ();
            if (event.getLevel().isClientSide()) {

            } else {
                ItemStack shitStack = new ItemStack(ModItems.SHIT.get(), 1);

                for (int i = 0; i < 1; i++) {
                    ItemEntity itemEntity;
                    itemEntity = new ItemEntity((Level) event.getLevel(), x, y, z, shitStack);
                    Random random = new Random();
                    itemEntity.setDeltaMovement(
                            (random.nextDouble() - 0.5) * 0.5,
                            random.nextDouble() * 0.8 + 0.2,
                            (random.nextDouble() - 0.5) * 0.5
                    );
                    event.getLevel().addFreshEntity(itemEntity);

                }


            }
        }
    }
}