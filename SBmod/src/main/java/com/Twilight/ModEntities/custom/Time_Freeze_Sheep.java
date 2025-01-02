package com.Twilight.ModEntities.custom;

import com.Twilight.ModSounds.ModSounds;
import com.Twilight.SBMod.Main;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.common.Mod;
import java.util.*;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class Time_Freeze_Sheep extends SheepOri implements IThrowerAware {
    private Player thrower;
    private static final double FREEZE_RADIUS = 10.0;
    private static final int FREEZE_DURATION = 200;
    private int freezeTimer = 0;
    private boolean isFreezingTime = false;
    private Map<Entity, EntityData> frozenEntities = new HashMap<>();
    private Map<Entity, EntityData> frozenPlayers = new HashMap<>();

    public Time_Freeze_Sheep(EntityType<? extends SheepOri> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void setThrower(Player player) {
        this.thrower = player;
    }

    @Override
    public Player getThrower() {
        return thrower;
    }

    private static class EntityData {
        Vec3 position;
        Vec3 motion;
        float yRot;
        float xRot;

        EntityData(Entity entity) {
            this.position = entity.position();
            this.motion = entity.getDeltaMovement();
            this.yRot = entity.getYRot();
            this.xRot = entity.getXRot();
        }
    }

    @Override
    public void baseTick() {
        if (!isFreezingTime) {
            super.baseTick();
            AABB searchBox = this.getBoundingBox().inflate(2);
            List<Entity> nearbyEntities = this.level().getEntities(this, searchBox);
            boolean shouldFreeze = false;
            for (Entity nearbyEntity : nearbyEntities) {
                if (nearbyEntity != this && nearbyEntity != getThrower()) {
                    shouldFreeze = true;
                    break;  // 找到一个符合条件的实体就退出循环
                }
            }

            // 如果附近有符合条件的实体，或者发生了碰撞，就触发冻结效果
            if (shouldFreeze || this.horizontalCollision || this.verticalCollision) {
                isFreezingTime = true;
                setNoGravity(true);
                this.setDeltaMovement(0, 0, 0);
                if (isFreezingTime && !this.level().isClientSide) {
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                                ModSounds.TIME_FREEZE.get(),
                                SoundSource.NEUTRAL, 1.0F, 1.0F);
                }
            }
        } else {
            if (freezeTimer < FREEZE_DURATION) {
                freezeTime();
                maintainFrozenState();
                freezeTimer++;
                spawnFreezeParticles();
            } else {
                unfreezeEntities();
                isFreezingTime = false;
                this.remove(Entity.RemovalReason.KILLED);
            }
        }
    }

    private void freezeTime() {
        AABB freezeArea = this.getBoundingBox().inflate(FREEZE_RADIUS);
        List<Entity> entitiesInRange = this.level().getEntitiesOfClass(Entity.class, freezeArea);

        for (Entity entity : entitiesInRange) {
            if (entity != this && !(entity instanceof Player) && !frozenEntities.containsKey(entity)) {
                frozenEntities.put(entity, new EntityData(entity));
                disableEntitySounds(entity);
                disableEntityAnimations(entity);
            } else if (entity != this && entity != getThrower() && entity instanceof Player && !frozenPlayers.containsKey(entity)) {
                frozenPlayers.put(entity, new EntityData(entity));
                disableEntitySounds(entity);
                disableEntityAnimations(entity);
            }
        }
    }

    private void maintainFrozenState() {
        //冻结非玩家实体
        for (Map.Entry<Entity, EntityData> entry : frozenEntities.entrySet()) {
            Entity entity = entry.getKey();
            EntityData data = entry.getValue();

            entity.setPos(data.position.x, data.position.y, data.position.z);
            entity.setDeltaMovement(Vec3.ZERO);
            entity.setYRot(data.yRot);
            entity.setXRot(data.xRot);
            if (entity instanceof Mob) {
                ((Mob) entity).setNoAi(true);
            }
            entity.setNoGravity(true);
        }
        //冻结玩家
        for (Map.Entry<Entity, EntityData> entry : frozenPlayers.entrySet()) {
            Entity entity = entry.getKey();
            EntityData data = entry.getValue();
            if (entity instanceof ServerPlayer player && entity != getThrower()) {
                // 锁定旋转和位置
                player.connection.send(new ClientboundPlayerPositionPacket(
                        data.position.x, data.position.y, data.position.z,
                        data.yRot, data.xRot,
                        Set.of(),
                        0
                ));

                // 锁定移动
                Vec3 newMotion = Vec3.ZERO;
                player.connection.send(new ClientboundSetEntityMotionPacket(player.getId(), newMotion));

                // 设置无重力
                player.setNoGravity(true);

                // 禁用玩家的交互能力
                player.stopUsingItem();
                player.closeContainer();

                // 设置选中的物品栏槽位为无效值
                int invalidSlot = 36;
                player.connection.send(new ClientboundSetCarriedItemPacket(invalidSlot));
                // 设置玩家的快捷栏选择为 36
                player.getInventory().selected = 36;
                player.connection.send(new ClientboundSetCarriedItemPacket(-1));

                // 禁用玩家蹲下
                player.setPose(Pose.STANDING);
                SynchedEntityData entityData = player.getEntityData();
                entityData.set(Player.DATA_POSE, Pose.STANDING);

                List<SynchedEntityData.DataValue<?>> dataValues = List.of(
                        SynchedEntityData.DataValue.create(Player.DATA_POSE, Pose.STANDING)
                );

                player.connection.send(new ClientboundSetEntityDataPacket(player.getId(), dataValues));

                //无法攻击和挖掘
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 1, 255, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 1, 255, false, false));

                // 同步到客户端
                player.connection.send(new ClientboundTeleportEntityPacket(player));

            }
        }
    }

    private void unfreezeEntities() {
        //解冻非玩家实体
        for (Map.Entry<Entity, EntityData> entry : frozenEntities.entrySet()) {
            Entity entity = entry.getKey();
            EntityData data = entry.getValue();

            entity.setDeltaMovement(data.motion);

            if (entity instanceof Mob) {
                ((Mob) entity).setNoAi(false);
            }
            entity.setNoGravity(false);
            enableEntitySounds(entity);
        }
        //解冻玩家
        for (Map.Entry<Entity, EntityData> entry : frozenPlayers.entrySet()) {
            Entity entity = entry.getKey();
            EntityData data = entry.getValue();

            if (entity instanceof ServerPlayer player) {
                player.setDeltaMovement(data.motion);
                entity.setNoGravity(false);
                player.getInventory().selected = 1;
                player.connection.send(new ClientboundSetCarriedItemPacket(1));
                enableEntitySounds(entity);
            }
        }
        frozenEntities.clear();
        frozenPlayers.clear();
    }

    @Override
    public void push(Entity entity) {
        if (!isFreezingTime) {
            super.push(entity);
        }
    }

    @Override
    public boolean isPushable() {
        return !isFreezingTime;
    }

    private void disableEntitySounds(Entity entity) {
        entity.setSilent(true);
    }

    private void enableEntitySounds(Entity entity) {
        entity.setSilent(false);
    }

    private void disableEntityAnimations(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            // 停止摆臂动画
            livingEntity.attackAnim = 0;
            livingEntity.oAttackAnim = 0;

            // 尝试停止其他动画
            livingEntity.tickCount = Integer.MIN_VALUE;
        }
    }

    private void spawnFreezeParticles() {
        if (!this.level().isClientSide) {
            ServerLevel serverLevel = (ServerLevel) this.level();
            double maxRadius = FREEZE_RADIUS;
            int particleCount = 60;

            if (isFreezingTime) {
                // 冻结效果持续期间，粒子向中心聚集
                double radius = maxRadius * (1 - (double) freezeTimer / FREEZE_DURATION);

                for (int i = 0; i < particleCount; i++) {
                    double theta = this.random.nextDouble() * Math.PI * 2;
                    double phi = this.random.nextDouble() * Math.PI;
                    double x = this.getX() + radius * Math.sin(phi) * Math.cos(theta);
                    double y = this.getY() + radius * Math.sin(phi) * Math.sin(theta);
                    double z = this.getZ() + radius * Math.cos(phi);

                    Vec3 particlePos = new Vec3(x, y, z);
                    Vec3 centerPos = this.position();
                    Vec3 direction = centerPos.subtract(particlePos).normalize();

                    double speed = 0.05;
                    double motionX = direction.x * speed;
                    double motionY = direction.y * speed;
                    double motionZ = direction.z * speed;

                    spawnMixedParticles(serverLevel, x, y, z, motionX, motionY, motionZ);
                }
            }
        }
    }

    private void spawnMixedParticles(ServerLevel serverLevel, double x, double y, double z, double motionX, double motionY, double motionZ) {
        switch (this.random.nextInt(3)) {
            case 0:
                serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, x, y, z, 1, motionX, motionY, motionZ, 0.1);
                break;
            case 1:
                serverLevel.sendParticles(ParticleTypes.END_ROD, x, y, z, 1, motionX, motionY, motionZ, 0.1);
                break;
            case 2:
                serverLevel.sendParticles(ParticleTypes.GLOW, x, y, z, 1, motionX, motionY, motionZ, 0.1);
                break;
        }
    }
    public boolean isAttackable() {
        return false;
    }
}



