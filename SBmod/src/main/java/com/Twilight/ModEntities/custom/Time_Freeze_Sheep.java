package com.Twilight.ModEntities.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Time_Freeze_Sheep extends SheepOri {
    private static final double FREEZE_RADIUS = 10.0;
    private static final int FREEZE_DURATION = 200;
    private int freezeTimer = 0;
    private boolean isFreezingTime = false;
    private Map<Entity, EntityData> frozenEntities = new HashMap<>();

    public Time_Freeze_Sheep(EntityType<? extends SheepOri> entityType, Level level) {
        super(entityType, level);
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
            if (nearbyEntities != this && nearbyEntities != thrower && this.horizontalCollision || this.verticalCollision) {
                isFreezingTime = true;
                setNoGravity(true);
                this.setDeltaMovement(0, 0, 0);
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
            }
        }
    }

    private void maintainFrozenState() {
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
    }

    private void unfreezeEntities() {
        for (Map.Entry<Entity, EntityData> entry : frozenEntities.entrySet()) {
            Entity entity = entry.getKey();
            EntityData data = entry.getValue();

            entity.setDeltaMovement(data.motion);

            if (entity instanceof Mob) {
                ((Mob) entity).setNoAi(false);
            }
            entity.setNoGravity(false);
        }
        frozenEntities.clear();
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
