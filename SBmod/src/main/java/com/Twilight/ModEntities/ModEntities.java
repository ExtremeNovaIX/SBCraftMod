package com.Twilight.ModEntities;

import com.Twilight.ModEntities.custom.*;
import com.Twilight.ModItems.ModItems;
import com.Twilight.ModItems.Resplendent_Blade;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import com.Twilight.SBMod.Main;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Main.MOD_ID);
    public static final RegistryObject<EntityType<Twilight_Builder>> TWILIGHT_BUILDER =
            ENTITIY_TYPES.register("twilight_builder",() -> EntityType.Builder.of(Twilight_Builder::new, net.minecraft.world.entity.MobCategory.CREATURE)
                    .sized(0.8f,1.8f).build("Twilight_Builder"));
    public static final RegistryObject<EntityType<Explosion_Sheep_Red>> EXPLOSION_SHEEP_RED =
            ENTITIY_TYPES.register("explosion_sheep_red", () -> EntityType.Builder.of(Explosion_Sheep_Red::new, net.minecraft.world.entity.MobCategory.CREATURE)
                    .sized(1.7f, 0.9f).build("explosion_sheep_red"));
    public static final RegistryObject<EntityType<Explosion_Sheep_Black>> EXPLOSION_SHEEP_BLACK =
            ENTITIY_TYPES.register("explosion_sheep_black", () -> EntityType.Builder.of(Explosion_Sheep_Black::new, net.minecraft.world.entity.MobCategory.CREATURE)
                    .sized(1.7f, 0.9f).build("explosion_sheep_black"));
    public static final RegistryObject<EntityType<Time_Freeze_Sheep>> TIME_FREEZE_SHEEP =
            ENTITIY_TYPES.register("time_freeze_sheep", () -> EntityType.Builder.of(Time_Freeze_Sheep::new, net.minecraft.world.entity.MobCategory.CREATURE)
                    .sized(1.7f, 0.9f).build("time_freeze_sheep"));
    public static final RegistryObject<EntityType<LaserEntity>> LASER_ENTITY =
            ENTITIY_TYPES.register("laser_entity",
            () -> EntityType.Builder.of((EntityType<LaserEntity> entityType, Level level) -> new LaserEntity(entityType, level), net.minecraft.world.entity.MobCategory.MISC)
                    .sized(0.5F, 0.5F) // 设置实体的大小
                    .clientTrackingRange(32) // 设置客户端追踪范围
                    .updateInterval(20) // 设置更新间隔
                    .build("laser_entity"));
    public static final RegistryObject<EntityType<Resplendent_BladeEntity>> RESPLENDENT_BLADE =
            ENTITIY_TYPES.register("resplendent_blade", () ->
                    EntityType.Builder.<Resplendent_BladeEntity>of(
                                    (type, level) -> new Resplendent_BladeEntity(
                                            (EntityType<? extends AbstractArrow>) type,
                                            level,
                                            ModItems.RESPLENDENT_BLADE.get()),
                                    MobCategory.MISC)
                            .sized(1F, 2F)
                            .build("resplendent_blade"));


    public static void register(IEventBus eventBus) {
        ENTITIY_TYPES.register(eventBus);
    }

}
