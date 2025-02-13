package com.Twilight.SBMod;

import com.Twilight.ModEntities.ModEntities;
import com.Twilight.ModEntities.client.*;
import com.Twilight.ModEntities.custom.Time_Freeze_Sheep;
import com.Twilight.ModSounds.ModSounds;
import com.Twilight.Packet.CustomPacket;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.server.ServerStartingEvent;
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
import com.Twilight.ModEntities.client.Twilight_BuilderRenderer;
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
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MOD_ID, "main"),
            () -> "1.0",
            s -> true,
            s -> true
    );
    public static void init() {
        // ... 其他初始化代码 ...

        int id = 0;
        PACKET_HANDLER.registerMessage(id++, CustomPacket.class, CustomPacket::encode, CustomPacket::decode, CustomPacket::handle);
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.TWILIGHT_BUILDER.get(), Twilight_BuilderRenderer::new);
            EntityRenderers.register(ModEntities.EXPLOSION_SHEEP_RED.get(), SheepRenderer::new);
            EntityRenderers.register(ModEntities.EXPLOSION_SHEEP_BLACK.get(), SheepRenderer::new);
            EntityRenderers.register(ModEntities.TIME_FREEZE_SHEEP.get(), SheepRenderer::new);
            EntityRenderers.register(ModEntities.LASER_ENTITY.get(), LaserRenderer::new);
        }
    }
     //Create Creative Mode Tab
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
