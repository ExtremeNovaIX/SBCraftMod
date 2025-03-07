package com.Twilight.ModEntities.custom;

import com.Twilight.ModItems.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import static com.Twilight.ModItems.Resplendent_Blade.breakBlocks;
import static com.Twilight.ModItems.Resplendent_Blade.hurtEnemyInBlade;

public class Resplendent_BladeEntity extends AbstractArrow {
    private final Item referenceItem;
    private int tickCount = 0;
    Resplendent_BladeEntity bladeEntity = this;
    private double speedMultiplier = 4;
    public Resplendent_BladeEntity(EntityType<? extends AbstractArrow> entityType, Level level,Item referenceItem) {
        super(entityType,level);
        this.referenceItem = referenceItem;
    }
    @Override
    public void tick() {
        super.tick();
        tickCount++;
        if(tickCount <= 60) {
            hurtEnemyInBlade(bladeEntity);
            breakBlocks(bladeEntity);
            Vec3 lookVec = getOwner().getLookAngle();
            bladeEntity.setDeltaMovement(lookVec.x * speedMultiplier, lookVec.y * speedMultiplier, lookVec.z * speedMultiplier);
        }
    }

    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(referenceItem);
    }
}
