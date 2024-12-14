package com.Twilight.ModItems;

import com.Twilight.ModEntities.ModEntities;
import com.Twilight.ModEntities.custom.Time_Freeze_Sheep;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Time_Freeze_SheepItem extends Item {
    public Time_Freeze_SheepItem(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);


        Explosion_Sheep_ItemOri.initializeAndSpawnSheep(level, player, itemStack,
                (l) -> new Time_Freeze_Sheep(ModEntities.TIME_FREEZE_SHEEP.get(), l));

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}

