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

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
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
        if (this.onGround()) {
            // 羊落地，生成爆炸
            this.explode();
        }
    }

    private void explode() {
        if (!this.level().isClientSide) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(),3F, Level.ExplosionInteraction.TNT);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }
}
