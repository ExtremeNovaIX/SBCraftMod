package com.Twilight.ModItems;

import com.Twilight.ModEntities.ModEntities;
import com.Twilight.ModEntities.custom.Explosion_Sheep_Black;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class Explosion_Sheep_BlackItem extends Item {
    public Explosion_Sheep_BlackItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        Explosion_Sheep_ItemOri.initializeAndSpawnSheep(level, player, itemStack,
                (l, p) -> {
                    Explosion_Sheep_Black sheep = new Explosion_Sheep_Black(ModEntities.EXPLOSION_SHEEP_BLACK.get(), l);
                    sheep.setThrower(p);  // 设置投掷者
                    return sheep;
                });

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
