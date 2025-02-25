package com.Twilight.ModEntities.custom;

import com.Twilight.ModItems.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class Resplendent_BladeEntity extends AbstractArrow {
    private final Item referenceItem;
    public Resplendent_BladeEntity(EntityType<? extends AbstractArrow> entityType, Level level,Item referenceItem) {
        super(entityType,level);
        this.referenceItem = referenceItem;
    }

    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(referenceItem);
    }
}
