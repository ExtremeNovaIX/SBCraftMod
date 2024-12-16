package com.Twilight.ModItems;

import com.Twilight.ModEntities.ModEntities;
import com.Twilight.ModEntities.custom.Explosion_Sheep_Red;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Explosion_Sheep_RedItem extends Item {
    public Explosion_Sheep_RedItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        Explosion_Sheep_ItemOri.initializeAndSpawnSheep(level, player, itemStack,
                (l, p) -> new Explosion_Sheep_Red(ModEntities.EXPLOSION_SHEEP_RED.get(), l));

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
