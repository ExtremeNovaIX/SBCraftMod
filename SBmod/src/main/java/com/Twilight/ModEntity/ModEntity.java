package com.Twilight.ModEntity;

import com.Twilight.SBMod.Main;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntity {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Main.MODID);

    public static final RegistryObject<EntityType<TwilightBuilderEntity>> TWILIGHT_BUILDER = ENTITIES.register("twilight_builder",
            () -> EntityType.Builder.of(TwilightBuilderEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.8F)
                    .build("twilight_builder"));
}
