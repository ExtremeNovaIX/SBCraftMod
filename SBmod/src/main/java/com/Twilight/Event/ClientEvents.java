package com.Twilight.Event;
import com.Twilight.ModEntities.ModEntities;
import com.Twilight.ModEntities.cilent.ModModelLayers;
import com.Twilight.ModEntities.cilent.Twilight_BuilderModel;
import com.Twilight.SBMod.Main;
import com.Twilight.Client.KeyBindings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.Twilight.ModEntities.cilent.Twilight_BuilderRenderer;
@Mod.EventBusSubscriber(modid = Main.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBindings.INSTANCE.SHIT_KEY);
    }
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(ModModelLayers.TWILIGHT_BUILDER_LAYER, Twilight_BuilderModel::createBodyLayer);
    }
}

