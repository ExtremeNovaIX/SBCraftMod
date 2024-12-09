package com.Twilight.SBMod;

import com.Twilight.ModEntities.ModEntities;
import com.Twilight.ModEntities.client.Explosion_SheepRenderer;
import com.Twilight.ModSounds.ModSounds;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.TWILIGHT_BUILDER.get(), Twilight_BuilderRenderer::new);
            EntityRenderers.register(ModEntities.EXPLOSION_SHEEP.get(), Explosion_SheepRenderer::new);
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
                // 添加更多物品
            })
            .build());

    private static final Logger LOGGER = LogUtils.getLogger();
    public Main() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        ModEntities.ENTITIY_TYPES.register(bus);
        CREATIVE_MODE_TABS.register(bus);
        ModSounds.register(bus);
        bus.addListener(this::addCreateTab);
    }

    public void addCreateTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == SBMOD_TAB.get()) {
            event.accept(HEAD_K2536_BLOCK_ITEM.get());
            event.accept(HEAD_EXTREMENOVAIX_BLOCK_ITEM.get());
            event.accept(HEAD_TWILIGHTBUILDER_BLOCK_ITEM.get());
            event.accept(SHIT.get());
            event.accept(OPERATION_LEAD_SEAL_DISC.get());
            event.accept(AMBIGUOUS_MORALITY_DISC.get());
            event.accept(MISTY_MEMORY_DISC.get());
            // 添加更多物品
        }
    }


}
