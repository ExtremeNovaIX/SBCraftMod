package com.Twilight.SBMod;

import com.Twilight.Client.KeyBindings;
import com.Twilight.ModEntities.ModEntities;
import com.Twilight.ModEntities.client.EntityLayers.ModModelLayers;
import com.Twilight.ModEntities.client.EntityModel.Twilight_BuilderModel;
import com.Twilight.ModEntities.client.Entityrenderer.LaserRenderer;
import com.Twilight.ModEntities.client.EntityModel.Explosion_SheepModel;
import com.Twilight.ModEntities.client.EntityModel.Explosion_Sheep_WoolModel;
import com.Twilight.ModEntities.client.Entityrenderer.Resplendent_BladeRenderer;
import com.Twilight.ModEntities.client.Entityrenderer.SheepRenderer;
import com.Twilight.ModEntities.custom.Resplendent_BladeEntity;
import com.Twilight.ModSounds.ModSounds;
import com.Twilight.Packet.CustomPacket;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import com.Twilight.ModEntities.client.Entityrenderer.Twilight_BuilderRenderer;
import static com.Twilight.ModBlock.ModBLock.BLOCKS;
import static com.Twilight.ModItems.ModItems.*;


// The value here should match an entry in the META-INF/mods.toml file
@Mod("sbmod")
public class Main {
    public static final String MOD_ID = "sbmod";
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    private void registerCommonEvents(IEventBus modEventBus) {
        ModEntities.ENTITIY_TYPES.register(modEventBus);
    }
    //网络包初始化
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MOD_ID, "main"),
            () -> "1.0",
            s -> true,
            s -> true
    );
    public static void init() {
        int id = 0;
        PACKET_HANDLER.registerMessage(id++, CustomPacket.class, CustomPacket::encode, CustomPacket::decode, CustomPacket::handle);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
    public static class ClientEvents {
        //注册实体渲染
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.TWILIGHT_BUILDER.get(), Twilight_BuilderRenderer::new);
            EntityRenderers.register(ModEntities.EXPLOSION_SHEEP_RED.get(), SheepRenderer::new);
            EntityRenderers.register(ModEntities.EXPLOSION_SHEEP_BLACK.get(), SheepRenderer::new);
            EntityRenderers.register(ModEntities.TIME_FREEZE_SHEEP.get(), SheepRenderer::new);
            EntityRenderers.register(ModEntities.LASER_ENTITY.get(), LaserRenderer::new);
            EntityRenderers.register(ModEntities.RESPLENDENT_BLADE.get(), Resplendent_BladeRenderer::new);
        }
        // 注册自定义物品渲染
        @SubscribeEvent
        public static void registerCustomItemRenderers(EntityRenderersEvent.RegisterRenderers event) {
            ItemProperties.register(RESPLENDENT_BLADE.get(),
                    new ResourceLocation("pulse_effect"),
                    (stack, level, entity, seed) -> 1.0F
            );
        }
        //注册按键
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBindings.INSTANCE.SHIT_KEY);
            event.register(KeyBindings.INSTANCE.RESPLENDENT_BLADE_MODE_SWICHKEY);
        }
        //注册实体层
        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
            event.registerLayerDefinition(ModModelLayers.TWILIGHT_BUILDER_LAYER, Twilight_BuilderModel::createBodyLayer);
            event.registerLayerDefinition(ModModelLayers.EXPLOSION_SHEEP_WOOL_LAYER, Explosion_Sheep_WoolModel::createBodyLayer);
            event.registerLayerDefinition(ModModelLayers.EXPLOSION_SHEEP_LAYER, Explosion_SheepModel::createBodyLayer);

        }
    }
     //创造模式物品栏
    public static final RegistryObject<CreativeModeTab> SBMOD_TAB = CREATIVE_MODE_TABS.register("sbmod_tab",
            () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(HEAD_K2536_BLOCK_ITEM.get()))
            .title(Component.translatable(MOD_ID))
            .displayItems((parameters, output) -> {
                output.accept(HEAD_K2536_BLOCK_ITEM.get());
                output.accept(HEAD_EXTREMENOVAIX_BLOCK_ITEM.get());
                output.accept(HEAD_TWILIGHTBUILDER_BLOCK_ITEM.get());
                output.accept(SHIT.get());
                output.accept(OPERATION_LEAD_SEAL_DISC.get());
                output.accept(AMBIGUOUS_MORALITY_DISC.get());
                output.accept(MISTY_MEMORY_DISC.get());
                output.accept(EXPLOSION_SHEEP_RED.get());
                output.accept(EXPLOSION_SHEEP_BLACK.get());
                output.accept(TIME_FREEZE_SHEEP.get());
                output.accept(THUNDER_ROD.get());
                output.accept(LASER_CANNON.get());
                output.accept(RESPLENDENT_BLADE.get());
                // 添加更多物品
            })
            .build());

    private static final Logger LOGGER = LogUtils.getLogger();
    public Main() {
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        registerCommonEvents(modEventBus);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        ModSounds.register(modEventBus);
        init();
    }
}

