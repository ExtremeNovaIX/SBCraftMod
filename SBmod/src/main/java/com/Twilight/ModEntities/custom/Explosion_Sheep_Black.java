package com.Twilight.ModEntities.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Explosion_Sheep_Black extends Explosion_SheepOri{
    public Explosion_Sheep_Black(EntityType<? extends Explosion_SheepOri> entityType, Level level) {
        super(entityType, level);
    }
    private boolean Landed = false;
    private static final double ATTRACTION_RANGE = 16.0; // 吸引范围，单位是方块
    private static final double ATTRACTION_STRENGTH = 0.3; // 吸引力强度

    @Override
    public void push(Entity entity) {
        if (Landed) {
            // 落地后不执行任何操作，防止被其他实体推动
        } else {
            super.push(entity);
        }
    }

    @Override
    public boolean isPushable() {
        return !Landed; // 表示这个实体不能被推动
    }

    @Override
    public void tick() {
        super.tick();
        spawnParticles();
        if (this.horizontalCollision || this.verticalCollision) {
           Landed = true;
        }
        if (!this.level().isClientSide()) {
            attractNearbyEntities();
            if (this.tickCount >= 200) {
                this.remove(Entity.RemovalReason.KILLED);
                }
            }
        }

    private void attractNearbyEntities() {
        // 定义搜索范围
        AABB searchBox = this.getBoundingBox().inflate(ATTRACTION_RANGE);
        List<Entity> nearbyEntities = this.level().getEntities(this, searchBox);
        for (Entity entity : nearbyEntities) {
            if (entity != this && entity instanceof LivingEntity && entity != thrower) {
                // 计算方向向量
                Vec3 attractionVector = this.position().subtract(entity.position());
                attractionVector = attractionVector.normalize().scale(ATTRACTION_STRENGTH);
                // 应用吸引力
                entity.setDeltaMovement(entity.getDeltaMovement().add(attractionVector));
            }
        }
    }
    protected void spawnParticles() {
        if (this.level().isClientSide) {
            double sheepX = this.getX();
            double sheepY = this.getY() + 0.5;
            double sheepZ = this.getZ();

            for (int i = 0; i < 10; i++) {
                // 在半径为3的球体范围内随机生成粒子
                double theta = random.nextDouble() * 2 * Math.PI;
                double phi = random.nextDouble() * Math.PI;
                double r = 3 * Math.cbrt(random.nextDouble());

                double offsetX = r * Math.sin(phi) * Math.cos(theta);
                double offsetY = r * Math.sin(phi) * Math.sin(theta);
                double offsetZ = r * Math.cos(phi);

                // 计算粒子到羊的方向向量
                Vec3 direction = new Vec3(offsetX, offsetY, offsetZ).normalize();

                // 设置粒子的初始速度，使其朝向羊
                double speed = 0.5;
                double velocityX = -direction.x * speed;
                double velocityY = -direction.y * speed;
                double velocityZ = -direction.z * speed;

                this.level().addParticle(
                        ParticleTypes.PORTAL, // 使用末影粒子
                        sheepX + offsetX,
                        sheepY + offsetY,
                        sheepZ + offsetZ,
                        velocityX, velocityY, velocityZ
                );
            }
        }
    }

}
