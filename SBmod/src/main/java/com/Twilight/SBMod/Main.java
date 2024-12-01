package com.Twilight.SBMod;

import com.Twilight.ModEntity.ModEntity;
import com.Twilight.ModEntity.TwilightBuilderEntity;
import com.Twilight.ModBlock.HeadK2536Block;
import com.Twilight.ModItems.Shit;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import com.Twilight.ModSound.ModSound;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MODID)
public class Main {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "sbmod";
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final RegistryObject<Block> HEAD_K2536_BLOCK = BLOCKS.register("head_k2536_block",
            () -> new HeadK2536Block(BlockBehaviour.Properties.of()
                    .strength(3.0f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL)));
    public static final RegistryObject<Block> HEAD_EXTREMENOVAIX_BLOCK = BLOCKS.register("head_extremenovaix_block", () -> new Block(BlockBehaviour.Properties.of().strength(1)));
    public static final RegistryObject<Block> HEAD_TWILIGHTBUILDER = BLOCKS.register("head_twilightbuilder_block", () -> new Block(BlockBehaviour.Properties.of().strength(1)));
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    //Create the heads of K2536 and ExtremenovaIX and Twilight_Builder

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<Item> HEAD_K2536_BLOCK_ITEM = ITEMS.register("head_k2536_block", () -> new BlockItem(HEAD_K2536_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> HEAD_EXTREMENOVAIX_BLOCK_ITEM = ITEMS.register("head_extremenovaix_block", () -> new BlockItem(HEAD_EXTREMENOVAIX_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> HEAD_TWILIGHTBUILDER_BLOCK_ITEM = ITEMS.register("head_twilightbuilder_block", () -> new BlockItem(HEAD_TWILIGHTBUILDER.get(), new Item.Properties()));
    //Create Shit
    public static final RegistryObject<Item> SHIT = ITEMS.register("shit", () -> new Shit(new Item.Properties()));
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    //Create Twilight_Builder

    public Main() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        ENTITIES.register(bus);
        CREATIVE_MODE_TABS.register(bus);
        ModEntity.ENTITIES.register(bus);
        ModSound.register(bus);
        bus.addListener(this::addCreateTab);
    }
    //Create Creative Mode Tab
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final RegistryObject<CreativeModeTab> SBMOD_TAB = CREATIVE_MODE_TABS.register("sbmod_tab", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(HEAD_K2536_BLOCK_ITEM.get()))
            .title(Component.translatable("MODID"))
            .displayItems((parameters, output) -> {
                output.accept(HEAD_K2536_BLOCK_ITEM.get());
                output.accept(HEAD_EXTREMENOVAIX_BLOCK_ITEM.get());
                output.accept(HEAD_TWILIGHTBUILDER_BLOCK_ITEM.get());
                output.accept(SHIT.get());

                // 添加更多物品
            })
            .build());
    public void addCreateTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == SBMOD_TAB.get()) {
            event.accept(HEAD_K2536_BLOCK_ITEM.get());
            event.accept(HEAD_EXTREMENOVAIX_BLOCK_ITEM.get());
            event.accept(HEAD_TWILIGHTBUILDER_BLOCK_ITEM.get());
            ModSound.getMusicDiscs().forEach(disc -> event.accept(disc.getItem().get()));
            event.accept(SHIT.get());
            // 添加更多物品
        }
    }
    private void setup(final FMLCommonSetupEvent event) {
        // ... 其他设置 ...
    }

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        ModEntity.registerEntityAttributes(event);
    }
}
