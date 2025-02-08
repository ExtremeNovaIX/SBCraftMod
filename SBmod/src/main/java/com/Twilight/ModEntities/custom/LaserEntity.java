package com.Twilight.ModEntities.custom;

import com.Twilight.ModEntities.ModEntities;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class LaserEntity extends Entity {
    // 数据同步定义
    private static final EntityDataAccessor<Vector3f> START =
            SynchedEntityData.defineId(LaserEntity.class, EntityDataSerializers.VECTOR3);
    private static final EntityDataAccessor<Vector3f> END =
            SynchedEntityData.defineId(LaserEntity.class, EntityDataSerializers.VECTOR3);

    public int age = 0;
    public final int maxAge = 20; // 存活时间（20 tick = 1秒）
    private float width = 0.5f;
    private int color = 0x800080; // ARGB格式颜色（紫色）

    public LaserEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noCulling = true;
        this.setInvulnerable(true);
    }

    public LaserEntity(Level level, Vec3 start, Vec3 end) {
        this(ModEntities.LASER_ENTITY.get(), level);
        setBeamPositions(start, end);
        this.setPos(start.x, start.y, start.z);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(START, new Vector3f());
        this.entityData.define(END, new Vector3f());
    }

    // 设置光束位置（服务端调用）
    public void setBeamPositions(Vec3 start, Vec3 end) {
        this.entityData.set(START, vec3ToVector(start));
        this.entityData.set(END, vec3ToVector(end));
    }

    // 获取坐标（自动转换类型）
    public Vec3 getStart() {
        return vectorToVec3(this.entityData.get(START));
    }

    public Vec3 getEnd() {
        return vectorToVec3(this.entityData.get(END));
    }

    // 类型转换方法
    private Vector3f vec3ToVector(Vec3 vec) {
        return new Vector3f((float)vec.x, (float)vec.y, (float)vec.z);
    }

    private Vec3 vectorToVec3(Vector3f vec) {
        return new Vec3(vec.x(), vec.y(), vec.z());
    }

    @Override
    public void tick() {
        super.tick();
        if (++this.age >= maxAge) {
            this.discard();
        }
    }

    // 序列化方法
    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.age = tag.getInt("Age");
        this.entityData.set(START, vec3ToVector(new Vec3(
                tag.getDouble("StartX"),
                tag.getDouble("StartY"),
                tag.getDouble("StartZ")
        )));
        this.entityData.set(END, vec3ToVector(new Vec3(
                tag.getDouble("EndX"),
                tag.getDouble("EndY"),
                tag.getDouble("EndZ")
        )));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("Age", this.age);
        Vec3 start = getStart();
        tag.putDouble("StartX", start.x);
        tag.putDouble("StartY", start.y);
        tag.putDouble("StartZ", start.z);
        Vec3 end = getEnd();
        tag.putDouble("EndX", end.x);
        tag.putDouble("EndY", end.y);
        tag.putDouble("EndZ", end.z);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true; // 强制渲染
    }

    // Getter方法
    public float getWidth() {
        return width;
    }

    public int getColor() {
        return color;
    }
}
