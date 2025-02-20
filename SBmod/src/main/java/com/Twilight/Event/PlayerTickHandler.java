package com.Twilight.Event;

import com.Twilight.ModItems.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.Twilight.ModItems.Resplendent_Blade.*;

@Mod.EventBusSubscriber
public class PlayerTickHandler {
    public static boolean canPlayerGetFallingHurt = true;
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        ItemStack mainHandItem = event.player.getMainHandItem();
        if (mainHandItem.getItem() == ModItems.RESPLENDENT_BLADE.get()) {
            canPlayerGetFallingHurt = false;
        }
        if(isDashingingTime){
            event.player.setNoGravity(true);
            breakBlocks(event.player,event.player.level());
            DashingTimer++;
            hurtEnemyInBlade(event.player);
            if(DashingTimer>=DashingTime){
                isDashingingTime = false;
                DashingTimer = 0;
                event.player.setNoGravity(false);
                event.player.fallDistance = 0;
            }
        }
    }
}
