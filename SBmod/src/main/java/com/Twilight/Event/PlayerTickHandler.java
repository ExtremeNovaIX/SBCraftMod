package com.Twilight.Event;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.Twilight.ModItems.Resplendent_Blade.*;

@Mod.EventBusSubscriber
public class PlayerTickHandler {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(isDashingingTime){
            event.player.setNoGravity(true);
            breakBlocks(event.player,event.player.level());
            DashingTimer++;
            if(DashingTimer>=DashingTime){
                isDashingingTime = false;
                DashingTimer = 0;
                event.player.setNoGravity(false);
            }
        }
    }
}
