package com.Twilight.ModItems;

import com.Twilight.ModEntities.ModEntities;
import com.Twilight.ModEntities.custom.Explosion_Sheep;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Random;

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
            playFireworkLaunchSound(level, player);
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
    private void playFireworkLaunchSound(Level level, Player player) {
        Random random = new Random();
        float pitch = random.nextFloat(1.5F)  + 0.5F;

        // 播放烟花发射音效
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.PLAYERS,
                1.0F, pitch);
    }
}
