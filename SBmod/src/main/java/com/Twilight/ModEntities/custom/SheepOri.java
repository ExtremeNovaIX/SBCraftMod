// FILEPATH: E:/SBCraft-mod/SBmod/src/main/java/com/Twilight/ModEntities/custom/Explosion_SheepOri.java

package com.Twilight.ModEntities.custom;

import com.Twilight.ModItems.Explosion_Sheep_ItemOri;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SheepOri extends Mob {
    public SheepOri(EntityType<? extends SheepOri> entityType, Level level) {
        super(entityType, level);
        this.addEffect(new MobEffectInstance(MobEffects.GLOWING, Integer.MAX_VALUE, 0, false, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D);
    }
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.type().msgId().equals("fall")) {
            return false;
        }
        return super.hurt(source, amount);
    }
}
