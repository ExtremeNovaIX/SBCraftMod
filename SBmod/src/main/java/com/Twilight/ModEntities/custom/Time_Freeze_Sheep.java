package com.Twilight.ModEntities.custom;

import com.Twilight.ModSounds.ModSounds;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.Twilight.ModItems.Explosion_Sheep_ItemOri.getThrower;
import static com.Twilight.ModItems.Explosion_Sheep_ItemOri.thrower;

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
                if(isFreezingTime && !this.level().isClientSide) {
                    this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                            ModSounds.TIME_FREEZE.get(),
                            SoundSource.NEUTRAL, 4.0F, 1.0F);
                }
            }
        } else {
            if (freezeTimer < FREEZE_DURATION) {
                freezeTime();
                maintainFrozenState();
                freezeTimer++;
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
            }else if(entity != this && entity != getThrower() && entity instanceof Player && !frozenPlayers.containsKey(entity)) {
                frozenPlayers.put(entity, new EntityData(entity));
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
        for(Map.Entry<Entity, EntityData> entry : frozenPlayers.entrySet()){
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
        }
        //解冻玩家
        for(Map.Entry<Entity, EntityData> entry : frozenPlayers.entrySet()){
            Entity entity = entry.getKey();
            EntityData data = entry.getValue();

            if (entity instanceof ServerPlayer player) {
                player.setDeltaMovement(data.motion);
                entity.setNoGravity(false);
                player.getInventory().selected = 1;
                player.connection.send(new ClientboundSetCarriedItemPacket(1));

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
}
