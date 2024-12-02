package com.Twilight.SBMod;

import com.Twilight.ModBlock.HeadK2536Block;
import com.Twilight.ModSounds.ModSounds;
import com.Twilight.Util.KeyBinding;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("sbmod")
public class Main {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "sbmod";
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final RegistryObject<Block> HEAD_K2536_BLOCK = BLOCKS.register("head_k2536_block",
            () -> new HeadK2536Block(BlockBehaviour.Properties.of()
                    .strength(3.0f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL)));
    public static final RegistryObject<Block> HEAD_EXTREMENOVAIX_BLOCK = BLOCKS.register("head_extremenovaix_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(1)));
    public static final RegistryObject<Block> HEAD_TWILIGHTBUILDER = BLOCKS.register("head_twilightbuilder_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(1)));
    public static final RegistryObject<Item> HEAD_K2536_BLOCK_ITEM = ITEMS.register("head_k2536_block",
            () -> new BlockItem(HEAD_K2536_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> HEAD_EXTREMENOVAIX_BLOCK_ITEM = ITEMS.register("head_extremenovaix_block",
            () -> new BlockItem(HEAD_EXTREMENOVAIX_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> HEAD_TWILIGHTBUILDER_BLOCK_ITEM = ITEMS.register("head_twilightbuilder_block",
            () -> new BlockItem(HEAD_TWILIGHTBUILDER.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHIT = ITEMS.register("shit",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> OPERATION_LEAD_SEAL_DISC = ITEMS.register("operation_lead_seal_disc",
            () -> new RecordItem(
                    15,
                    ModSounds.OPERATION_LEAD_SEAL_DISC,
                    new Item.Properties().stacksTo(1).rarity(Rarity.RARE),
                    2760
            )
    );

    public static final RegistryObject<Item> AMBIGUOUS_MORALITY_DISC = ITEMS.register("ambiguous_morality_disc",
            () -> new RecordItem(
                    15,
                    ModSounds.AMBIGUOUS_MORALITY_DISC,
                    new Item.Properties().stacksTo(1).rarity(Rarity.RARE),
                    4700
            )
    );

    public static final RegistryObject<Item> MISTY_MEMORY_DISC = ITEMS.register("misty_memory_disc",
            () -> new RecordItem(
                    15,
                    ModSounds.MISTY_MEMORY_DISC,
                    new Item.Properties().stacksTo(1).rarity(Rarity.RARE),
                    4840
            )
    );
     //Create Creative Mode Tab
    public static final RegistryObject<CreativeModeTab> SBMOD_TAB = CREATIVE_MODE_TABS.register("sbmod_tab",
            () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(HEAD_K2536_BLOCK_ITEM.get()))
            .title(Component.translatable("MOD_ID"))
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
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public Main() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        ENTITIES.register(bus);
        CREATIVE_MODE_TABS.register(bus);
        ModSounds.register(bus);
        bus.addListener(this::addCreateTab);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


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

    private void setup(final FMLCommonSetupEvent event) {
        // ... 其他设置 ...
    }


}
