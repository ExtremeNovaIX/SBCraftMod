package com.Twilight.ModItems;

import com.Twilight.ModEntities.ModEntities;
import com.Twilight.ModEntities.custom.Explosion_Sheep;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ExplosionSheepSpawnItem extends Item {
    public ExplosionSheepSpawnItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            Explosion_Sheep sheep = new Explosion_Sheep(ModEntities.EXPLOSION_SHEEP.get(), level);
            sheep.setPos(player.getX(), player.getY() + 1.0, player.getZ());
            sheep.setYRot(player.getYRot());
            sheep.setXRot(player.getXRot());
            sheep.setDeltaMovement(player.getLookAngle().multiply(3, 3, 3));
            level.addFreshEntity(sheep);

            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
