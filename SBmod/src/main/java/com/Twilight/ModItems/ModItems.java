package com.Twilight.ModItems;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.Twilight.SBMod.Main;

import static com.Twilight.SBMod.Main.MODID;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    // 注册Shit物品
    public static final RegistryObject<Item> SHIT = ITEMS.register("shit", () -> new Shit(new Item.Properties()));

    // 不再需要 getShit() 方法
}