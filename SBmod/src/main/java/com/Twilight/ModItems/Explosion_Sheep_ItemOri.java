// FILEPATH: E:/SBCraft-mod/SBmod/src/main/java/com/Twilight/ModEntities/Explosion_Sheep_Init.java

package com.Twilight.ModItems;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Random;
import java.util.function.Function;

public class Explosion_Sheep_ItemOri {
    public static Player thrower;
    public static <T extends Mob> void initializeAndSpawnSheep(Level level, Player player, ItemStack itemStack, Function<Level, T> sheepSupplier) {
        thrower = player;
        if (!level.isClientSide()) {
            T sheep = sheepSupplier.apply(level);
            sheep.setPos(player.getX() + 0.5, player.getY() + 1.0, player.getZ() + 0.5);
            sheep.setYRot(player.getYRot());
            sheep.setXRot(player.getXRot());
            sheep.setDeltaMovement(player.getLookAngle().multiply(4, 4, 4));
            level.addFreshEntity(sheep);
            playFireworkLaunchSound(level, player);
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
        }
    }

    public static void playFireworkLaunchSound(Level level, Player player) {
        Random random = new Random();
        float pitch = random.nextFloat(1.5F)  + 0.5F;

        // 播放烟花发射音效
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.PLAYERS,
                1.0F, pitch);
    }
    public static Player getThrower() {
        return thrower;
    }
}
