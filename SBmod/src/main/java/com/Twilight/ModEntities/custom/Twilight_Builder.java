package com.Twilight.ModEntities.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.entity.ai.attributes.Attributes;
import com.Twilight.ModItems.ModItems;
public class Twilight_Builder extends Animal {

    public Twilight_Builder(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0,new BreedGoal(this,2.0D));
        this.goalSelector.addGoal(0,new TemptGoal(this,2.5D, Ingredient.of(ModItems.SHIT.get()), false));
        this.goalSelector.addGoal(4,new RandomStrollGoal(this,1.1D));
        this.goalSelector.addGoal(5,new LookAtPlayerGoal(this, Player.class,5.0f));
        this.goalSelector.addGoal(6,new RandomLookAroundGoal(this));

    }




    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 1.5D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0D);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }
}
