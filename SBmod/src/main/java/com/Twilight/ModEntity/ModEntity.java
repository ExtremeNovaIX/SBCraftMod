package com.Twilight.ModEntity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;

import static com.Twilight.SBMod.Main.ENTITIES;

public class ModEntity {
    public static final RegistryObject<EntityType<TwilightBuilderEntity>> TWILIGHT_BUILDER = ENTITIES.register("twilight_builder",
            () -> EntityType.Builder.<TwilightBuilderEntity>of(TwilightBuilderEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.8F)
                    .build("twilight_builder"));
}
