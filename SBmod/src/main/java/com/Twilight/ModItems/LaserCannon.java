package com.Twilight.ModItems;

import com.Twilight.ModSounds.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class LaserCannon extends Item {
    public LaserCannon(Item.Properties properties) {
        super(properties);
    }
    private SoundInstance shootingSoundInstance;
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                ModSounds.LASER_CANNON_IN_HAND.get(),
                SoundSource.NEUTRAL, 1.0F, 1.0F);

        Vec3 lookVec = player.getLookAngle();

        int distance = 60;
        AABB boundingBox = new AABB(
                player.getBoundingBox().minX - distance, player.getBoundingBox().minY - distance,
                player.getBoundingBox().minZ - distance, player.getBoundingBox().maxX + distance,
                player.getBoundingBox().maxY + distance, player.getBoundingBox().maxZ + distance
        );

        List<Entity> nearbyEntities = level.getEntities(player, boundingBox);

        for (Entity entity : nearbyEntities) {
            for (int i = 0; i < 10; i++) {
                Vec3 entityVec = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                Vec3 playerToEntityVec = entityVec.subtract(player.position());
                double offsetX = Math.random() * 0.1 - 0.05; // 在X方向上随机偏移
                double offsetY = Math.random() * 0.1 - 0.05; // 在Y方向上随机偏移
                double offsetZ = Math.random() * 0.1 - 0.05; // 在Z方向上随机偏移
                Vec3 particlePos = player.position().add(lookVec.scale(i)).add(offsetX, offsetY, offsetZ);
                level.addParticle(ParticleTypes.PORTAL, particlePos.x, particlePos.y, particlePos.z, 0, 0, 0);
                if (entity instanceof LivingEntity && !(entity instanceof Player)) { // 排除玩家
                    if (playerToEntityVec.normalize().dot(lookVec.normalize()) > 0.996) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        livingEntity.hurt(level.damageSources().magic(), 4.5f);
                    }

                }
            }
        }

        return super.use(level, player, hand);
    }

}
