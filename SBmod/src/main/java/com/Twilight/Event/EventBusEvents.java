package com.Twilight.Event;

import com.Twilight.ModEntities.ModEntities;
import com.Twilight.ModEntities.custom.Explosion_SheepOri;
import com.Twilight.ModEntities.custom.Twilight_Builder;
import com.Twilight.SBMod.Main;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)

public class EventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.TWILIGHT_BUILDER.get(), Twilight_Builder.createAttributes().build());
        event.put(ModEntities.EXPLOSION_SHEEP_RED.get(), Explosion_SheepOri.createAttributes().build());
    }
}