package com.Twilight.ModEntities;

import com.Twilight.ModEntities.custom.Explosion_SheepOri;
import com.Twilight.ModEntities.custom.Explosion_Sheep_Black;
import com.Twilight.ModEntities.custom.Explosion_Sheep_Red;
import com.Twilight.ModEntities.custom.Twilight_Builder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import com.Twilight.SBMod.Main;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Main.MOD_ID);
    public static final RegistryObject<EntityType<Twilight_Builder>> TWILIGHT_BUILDER =
            ENTITIY_TYPES.register("twilight_builder",() -> EntityType.Builder.of(Twilight_Builder::new, MobCategory.CREATURE)
                    .sized(0.8f,1.8f).build("Twilight_Builder"));
    public static final RegistryObject<EntityType<Explosion_Sheep_Red>> EXPLOSION_SHEEP_RED =
            ENTITIY_TYPES.register("explosion_sheep_red", () -> EntityType.Builder.of(Explosion_Sheep_Red::new, MobCategory.CREATURE)
                    .sized(1.7f, 0.9f).build("explosion_sheep_red"));
    public static final RegistryObject<EntityType<Explosion_Sheep_Black>> EXPLOSION_SHEEP_BLACK =
            ENTITIY_TYPES.register("explosion_sheep_black", () -> EntityType.Builder.of(Explosion_Sheep_Black::new, MobCategory.CREATURE)
                    .sized(1.7f, 0.9f).build("explosion_sheep_black"));


    public static void register(IEventBus eventBus) {
        ENTITIY_TYPES.register(eventBus);
    }

}
