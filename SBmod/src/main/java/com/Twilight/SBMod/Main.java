package com.Twilight.SBMod;

import com.Twilight.Entity.TwilightBuilderEntity;
import com.Twilight.block.head_k2536_block;
import com.Twilight.item.Shit;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MODID)
public class Main {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "sbmod";
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final RegistryObject<Block> HEAD_K2536_BLOCK = BLOCKS.register("head_k2536_block",
            () -> new head_k2536_block(BlockBehaviour.Properties.of()
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
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final RegistryObject<EntityType<TwilightBuilderEntity>> TWILIGHT_BUILDER = ENTITIES.register("twilight_builder",
            () -> EntityType.Builder.<TwilightBuilderEntity>of(TwilightBuilderEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.8F)
                    .build("twilight_builder"));
    public Main() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        ENTITIES.register(bus);
    }
}
