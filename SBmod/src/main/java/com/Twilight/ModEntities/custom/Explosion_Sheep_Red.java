package com.Twilight.ModEntities.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class Explosion_Sheep_Red extends Mob {
    public Explosion_Sheep_Red(EntityType<? extends Explosion_Sheep_Red> entityType, Level level) {
        super(entityType, level);
        this.addEffect(new MobEffectInstance(MobEffects.GLOWING, Integer.MAX_VALUE, 0, false, false));
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
        spawnFlameParticles();
        // 检查羊是否在空中
        if (this.horizontalCollision || this.verticalCollision) {
            // 羊落地，生成爆炸
            this.explode();
        }
    }

    private void explode() {
        if (!this.level().isClientSide) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(),4F, Level.ExplosionInteraction.TNT);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }
    private void spawnFlameParticles() {
        if (this.level().isClientSide) {
            double x = this.getX();
            double y = this.getY() + 0.5;
            double z = this.getZ();

            for (int i = 0; i < 5; i++) {
                double offsetX = random.nextDouble() * 0.2 - 0.1;
                double offsetY = random.nextDouble() * 0.2 - 0.1;
                double offsetZ = random.nextDouble() * 0.2 - 0.1;

                this.level().addParticle(
                        ParticleTypes.FLAME,
                        x + offsetX,
                        y + offsetY,
                        z + offsetZ,
                        0, 0, 0
                );
            }
        }
    }
}
