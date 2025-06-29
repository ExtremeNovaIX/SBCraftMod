package com.Twilight.ModItems;

import com.Twilight.ModSounds.ModSounds;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.Twilight.ModBlock.ModBLock.*;
import static com.Twilight.SBMod.Main.MOD_ID;
import static net.minecraft.world.item.Tiers.DIAMOND;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final RegistryObject<Item> SHIT = ITEMS.register("shit",
            () -> new Shit(new Item.Properties()));
    public static final RegistryObject<Item> TIME_FREEZE_SHEEP = ITEMS.register("time_freeze_sheep",
            () -> new Time_Freeze_SheepItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> EXPLOSION_SHEEP_RED = ITEMS.register("explosion_sheep_red",
            () -> new Explosion_Sheep_RedItem(new Item.Properties()));
    public static final RegistryObject<Item> EXPLOSION_SHEEP_BLACK = ITEMS.register("explosion_sheep_black",
            () -> new Explosion_Sheep_BlackItem(new Item.Properties()));
    public static final RegistryObject<Item> HEAD_K2536_BLOCK_ITEM = ITEMS.register("head_k2536_block",
            () -> new BlockItem(HEAD_K2536_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> HEAD_EXTREMENOVAIX_BLOCK_ITEM = ITEMS.register("head_extremenovaix_block",
            () -> new BlockItem(HEAD_EXTREMENOVAIX_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> HEAD_TWILIGHTBUILDER_BLOCK_ITEM = ITEMS.register("head_twilightbuilder_block",
            () -> new BlockItem(HEAD_TWILIGHTBUILDER.get(), new Item.Properties()));
    public static final RegistryObject<Item> RESPLENDENT_BLADE = ITEMS.register("resplendent_blade",
            () -> new Resplendent_Blade(DIAMOND, 50, 0.01f, new Item.Properties()));
    public static final RegistryObject<Item> OPERATION_LEAD_SEAL_DISC = ITEMS.register("operation_lead_seal_disc",
            () -> new RecordItem(
                    15,
                    ModSounds.OPERATION_LEAD_SEAL_DISC,
                    new Item.Properties().stacksTo(1).rarity(Rarity.RARE),
                    2760
            )
    );
    public static final RegistryObject<Item> THUNDER_ROD = ITEMS.register("thunder_rod",
            () -> new ThunderRod(new Item.Properties()));

    public static final RegistryObject<Item> LASER_CANNON = ITEMS.register("laser_cannon",
            () -> new LaserCannon(new Item.Properties()));

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

    public static final RegistryObject<Item> THROWABLE_FIREBALL = ITEMS.register("throwable_fireball",
            () -> new Throwable_Fireball(
                    new Item.Properties()
                            .stacksTo(64)
            )
    );
}