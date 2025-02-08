package com.Twilight.Event;

import com.Twilight.ModEntities.ModEntities;
import com.Twilight.ModEntities.client.LaserRenderer;
import com.Twilight.ModEntities.custom.SheepOri;
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
        event.put(ModEntities.EXPLOSION_SHEEP_RED.get(), SheepOri.createAttributes().build());
        event.put(ModEntities.EXPLOSION_SHEEP_BLACK.get(), SheepOri.createAttributes().build());
        event.put(ModEntities.TIME_FREEZE_SHEEP.get(), SheepOri.createAttributes().build());
    }
}