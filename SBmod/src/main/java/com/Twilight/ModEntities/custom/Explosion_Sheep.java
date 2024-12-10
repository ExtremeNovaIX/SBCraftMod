package com.Twilight.ModEntities.custom;

import com.Twilight.Packet.CustomPacket;
import com.Twilight.SBMod.Main;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class Explosion_Sheep extends Mob {
    public Explosion_Sheep(EntityType<? extends Explosion_Sheep> entityType, Level level) {
        super(entityType, level);
    }

    private boolean wasInAir = false;

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        // 检查伤害源是否为摔落伤害
        if (source.type().msgId().equals("fall")) {
            // 如果是摔落伤害，返回false表示不受伤害
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public void tick() {
        super.tick();

        // 检查羊是否在空中
        boolean isInAir = !this.onGround();

        // 如果羊之前在空中，现在在地面上，那么它刚刚落地
        if (wasInAir && !isInAir) {
            // 羊落地，生成爆炸
            this.explode();
        }
        wasInAir = isInAir;
    }

    private void explode() {
        if (!this.level().isClientSide) {
            // 发包到服务器
            Main.PACKET_HANDLER.sendToServer(new CustomPacket("ExplosionSheepLanded:" + this.getId()));
        }
    }
}
