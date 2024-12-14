package com.Twilight.ModEntities.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Explosion_Sheep_Red extends SheepOri {
    public Explosion_Sheep_Red(EntityType<? extends Explosion_Sheep_Red> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        spawnParticles();
        if (this.horizontalCollision || this.verticalCollision) {
            this.explode();
        }
    }


    protected void explode() {
        if (!this.level().isClientSide) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 4F, Level.ExplosionInteraction.TNT);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }


    protected void spawnParticles() {
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
