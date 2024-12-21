package com.Twilight.ModItems;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;


import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.world.entity.EntityType.LIGHTNING_BOLT;

public class ThunderRod extends Item {
    public ThunderRod(Properties properties) {
        super(properties);
    }

    // 添加物品描述
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.sbmod.thunder_rod.desc").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        Vec3 lookVec = player.getLookAngle();

        int distance = 40;
        AABB boundingBox = new AABB(
                player.getBoundingBox().minX - distance, player.getBoundingBox().minY - distance,
                player.getBoundingBox().minZ - distance, player.getBoundingBox().maxX + distance,
                player.getBoundingBox().maxY + distance, player.getBoundingBox().maxZ + distance
        );

        List<Entity> nearbyEntities = level.getEntities(player, boundingBox);

        for (Entity entity : nearbyEntities) {
            if (entity instanceof LivingEntity && !(entity instanceof Player)) { // 排除玩家
                // 计算玩家到实体的向量
                Vec3 entityVec = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                Vec3 playerToEntityVec = entityVec.subtract(player.position());

                // 检查向量是否与玩家的视线方向相似
                if (playerToEntityVec.normalize().dot(lookVec.normalize()) > 0.988) {
                    double x = entity.getX();
                    double y = entity.getY() + entity.getEyeHeight();
                    double z = entity.getZ();

                    LightningBolt lightningBolt = new LightningBolt(LIGHTNING_BOLT, level);
                    lightningBolt.moveTo(x, y, z);

                    level.addFreshEntity(lightningBolt);
                }
            }
        }

        return super.use(level, player, hand);
    }

}
