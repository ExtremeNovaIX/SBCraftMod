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
    private static final EntityDataAccessor<Integer> COLOR =
            SynchedEntityData.defineId(LaserEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> WIDTH =
            SynchedEntityData.defineId(LaserEntity.class, EntityDataSerializers.FLOAT);

    // 配置参数
    private int age = 0;
    private final int maxAge = 20; // 存活时间（20 tick = 1秒）

    public LaserEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noCulling = true;
        this.setInvulnerable(true);
    }

    // 完整构造方法
    public LaserEntity(Level level, Vec3 start, Vec3 end, int color, float width) {
        this(ModEntities.LASER_ENTITY.get(), level);
        this.setBeamPositions(start, end);
        this.setColor(color);
        this.setWidth(width);
        this.setPos(start.x, start.y, start.z);
    }

    // 简化构造方法
    public LaserEntity(Level level, Vec3 start, Vec3 end) {
        this(level, start, end, 0xCC88FFFF, 0.5f);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(START, new Vector3f());
        this.entityData.define(END, new Vector3f());
        this.entityData.define(COLOR, 0xCC88FFFF); // 默认淡紫色（ARGB）
        this.entityData.define(WIDTH, 0.5f);
    }

    // 光束位置控制
    public void setBeamPositions(Vec3 start, Vec3 end) {
        this.entityData.set(START, vec3ToVector(start));
        this.entityData.set(END, vec3ToVector(end));
    }

    // 颜色控制（ARGB格式）
    public void setColor(int argbColor) {
        this.entityData.set(COLOR, argbColor);
    }
    public int getColor() {
        return this.entityData.get(COLOR);
    }

    // 宽度控制
    public void setWidth(float width) {
        this.entityData.set(WIDTH, width);
    }
    public float getWidth() {
        return this.entityData.get(WIDTH);
    }

    // 坐标获取
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

    // 持久化存储
    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.age = tag.getInt("Age");
        this.setColor(tag.getInt("Color"));
        this.setWidth(tag.getFloat("Width"));
        this.setBeamPositions(new Vec3(
                tag.getDouble("StartX"),
                tag.getDouble("StartY"),
                tag.getDouble("StartZ")
        ), new Vec3(
                tag.getDouble("EndX"),
                tag.getDouble("EndY"),
                tag.getDouble("EndZ")
        ));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("Age", this.age);
        tag.putInt("Color", this.getColor());
        tag.putFloat("Width", this.getWidth());

        Vec3 start = this.getStart();
        tag.putDouble("StartX", start.x);
        tag.putDouble("StartY", start.y);
        tag.putDouble("StartZ", start.z);

        Vec3 end = this.getEnd();
        tag.putDouble("EndX", end.x);
        tag.putDouble("EndY", end.y);
        tag.putDouble("EndZ", end.z);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true; // 强制渲染
    }
}
