package com.Twilight.ModItems;

import com.Twilight.ModEntities.ModEntities;
import com.Twilight.ModEntities.custom.Explosion_Sheep_Black;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Explosion_Sheep_BlackItem extends Item {
    public Explosion_Sheep_BlackItem(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        Explosion_Sheep_ItemOri.initializeAndSpawnSheep(level, player, itemStack,
                (l) -> new Explosion_Sheep_Black(ModEntities.EXPLOSION_SHEEP_BLACK.get(), l));

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
