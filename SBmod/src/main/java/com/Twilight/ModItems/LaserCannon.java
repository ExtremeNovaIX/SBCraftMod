package com.Twilight.ModItems;

import com.Twilight.ModEntities.custom.LaserEntity;
import com.Twilight.ModSounds.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class LaserCannon extends Item {
    public LaserCannon(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                ModSounds.LASER_CANNON_IN_HAND.get(),
                SoundSource.NEUTRAL, 1.0F, 1.0F);
        spawnLaser(level, player);
        return super.use(level, player, hand);
    }

    public void spawnLaser(Level level, Player player) {
        if (!level.isClientSide) { // 确保只在服务端生成实体
            Vec3 start = player.getEyePosition(1.0F);
            Vec3 end = start.add(player.getLookAngle().scale(50));
            LaserEntity laser = new LaserEntity(level, start, end);
            level.addFreshEntity(laser);

            //player.sendSystemMessage(Component.literal("生成激光实体于: " + start + " 到 " + end));
        }
    }
}
