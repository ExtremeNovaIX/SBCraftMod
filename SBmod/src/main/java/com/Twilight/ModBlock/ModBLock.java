package com.Twilight.ModBlock;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.Twilight.SBMod.Main.MOD_ID;

public class ModBLock {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final RegistryObject<Block> HEAD_K2536_BLOCK = BLOCKS.register("head_k2536_block",
            () -> new HeadK2536Block(BlockBehaviour.Properties.of()
                    .strength(3.0f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL)));
    public static final RegistryObject<Block> HEAD_EXTREMENOVAIX_BLOCK = BLOCKS.register("head_extremenovaix_block", () -> new Block(BlockBehaviour.Properties.of().strength(1)));
    public static final RegistryObject<Block> HEAD_TWILIGHTBUILDER = BLOCKS.register("head_twilightbuilder_block", () -> new Block(BlockBehaviour.Properties.of().strength(1)));
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);

    //Create the heads of K2536 and ExtremenovaIX and Twilight_Builder

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final RegistryObject<Item> HEAD_K2536_BLOCK_ITEM = ITEMS.register("head_k2536_block", () -> new BlockItem(HEAD_K2536_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> HEAD_EXTREMENOVAIX_BLOCK_ITEM = ITEMS.register("head_extremenovaix_block", () -> new BlockItem(HEAD_EXTREMENOVAIX_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> HEAD_TWILIGHTBUILDER_BLOCK_ITEM = ITEMS.register("head_twilightbuilder_block", () -> new BlockItem(HEAD_TWILIGHTBUILDER.get(), new Item.Properties()));
}
