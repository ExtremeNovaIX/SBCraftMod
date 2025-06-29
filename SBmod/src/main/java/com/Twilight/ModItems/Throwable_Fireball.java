package com.Twilight.ModItems;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Throwable_Fireball extends Item {
    public Throwable_Fireball(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            // 创建恶魂火球实体
            LargeFireball largeFireball = new LargeFireball(
                    level,
                    player,
                    player.getLookAngle().x * 2.0,
                    player.getLookAngle().y * 2.0,
                    player.getLookAngle().z * 2.0,
                    5
            ){
                @Override
                protected void onHit(HitResult result) {
                    super.onHit(result);
                    // 增加爆炸击退效果
                    level.explode(this, this.getX(), this.getY(), this.getZ(),
                            8, true, Level.ExplosionInteraction.MOB);
                }
            };
            largeFireball.setPos(player.getX(), player.getEyeY() - 1, player.getZ());
            level.addFreshEntity(largeFireball);
            return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
        }
        return InteractionResultHolder.pass(itemStack);
    }
}
