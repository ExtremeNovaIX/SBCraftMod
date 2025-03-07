package com.Twilight.ModItems;

import com.Twilight.Event.PlayerTickHandler;
import com.Twilight.ModEntities.ModEntities;
import com.Twilight.ModEntities.custom.Resplendent_BladeEntity;
import com.Twilight.ModSounds.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;

import static com.Twilight.Event.PlayerTickHandler.dashDirections;
import static com.Twilight.ModItems.ModItems.RESPLENDENT_BLADE;

@Mod.EventBusSubscriber
public class Resplendent_Blade extends SwordItem {
    public static int DashingTime = 10;//在这里设置冲刺时间(tick)


    // 添加物品描述
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.sbmod.resplendent_blade.desc").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, level, tooltip, flag);
    }


    enum SwordMode {
        NORMAL,
        DASHING,
        DEFENDING
    }

    public SwordMode swordMode = SwordMode.NORMAL; // 初始模式

    //循环切换模式
    public void cycleMode(Player player) {
        switch (this.swordMode) {
            case NORMAL:
                this.swordMode = SwordMode.DASHING;
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        ModSounds.RESPLENDENT_BLADE_DASHING_START.get(), SoundSource.PLAYERS,
                        0.5F, 1.0F);
                break;
            case DASHING:
                this.swordMode = SwordMode.DEFENDING;
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        ModSounds.RESPLENDENT_BLADE_DEFENDING.get(), SoundSource.PLAYERS,
                        0.5F, 1.0F);
                break;
            case DEFENDING:
                this.swordMode = SwordMode.NORMAL;
                break;
        }
    }

    public Resplendent_Blade(Tier material, int damage, float speed, Properties properties) {
        super(material, damage, speed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity attacker, LivingEntity target) {
        Level level = attacker.level();
        if (!level.isClientSide) {
            switch (swordMode) {
                case DEFENDING:
                case NORMAL:
                    level.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(),
                            ModSounds.RESPLENDENT_BLADE_NORMAL.get(),
                            SoundSource.PLAYERS, 0.5F, 1.0F);
                    break;
                case DASHING:
                    level.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(),
                            ModSounds.RESPLENDENT_BLADE_DASHING.get(),
                            SoundSource.PLAYERS, 0.5F, 1.0F);
                    break;
            }
        }
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (this.swordMode == SwordMode.DASHING) {
            Dashing(player);
            return InteractionResultHolder.success(stack);
        }else if (this.swordMode == SwordMode.DEFENDING) {
            throwBlade(player);
        }
        return InteractionResultHolder.pass(stack);
    }

    public static void Dashing(Player player) {
        Level level = player.level();
        if (!level.isClientSide) {
            // 播放声音
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    ModSounds.RESPLENDENT_BLADE_DASHING.get(),
                    SoundSource.PLAYERS, 10F, 1.0F);

            double maxSpeed = 5.0; // 最大速度
            Vec3 lookVec = player.getLookAngle();
            Vec3 direction = lookVec.normalize().scale(maxSpeed);

            PlayerTickHandler.startDash(player);
            player.setNoGravity(true);
            dashDirections.put(player.getUUID(), direction);

            // 对于玩家，使用网络包来设置移动
            if (player instanceof ServerPlayer serverPlayer) {
                Vec3 newMotion = serverPlayer.getDeltaMovement().add(direction);
                serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer.getId(), newMotion));
            }
            PlayerTickHandler.startDash(player);
            player.setNoGravity(true);
        }
    }

    public static void breakBlocks(Resplendent_BladeEntity bladeEntity) {
        Player player = (Player) bladeEntity.getOwner();
        Level level = player.level();
        double range = 7.0; // 检测范围，单位是方块
        for (int x = (int) (Math.floor(bladeEntity.getX()) - range); x < Math.floor(bladeEntity.getX()) + range; x++) {
            for (int y = (int) (Math.floor(bladeEntity.getY()) - range); y < Math.floor(bladeEntity.getY()) + range; y++) {
                for (int z = (int) (Math.floor(bladeEntity.getZ()) - range); z < Math.floor(bladeEntity.getZ()) + range; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    // 计算当前位置与玩家位置之间的距离，如果小于等于范围，则破坏方块
                    if (Math.sqrt(Math.pow(x - bladeEntity.getX(), 2) + Math.pow(y - bladeEntity.getY(), 2) + Math.pow(z - bladeEntity.getZ(), 2)) <= range) {
                        BlockState state = level.getBlockState(pos);
                        if (!state.isAir()) { // 检查方块是否为空
                            level.removeBlock(pos, false); // 破坏方块
                            level.sendBlockUpdated(pos, Blocks.AIR.defaultBlockState(), state, 7);
                        }
                    }
                }
            }
        }
    }

    public static void hurtEnemyInBlade(Resplendent_BladeEntity bladeEntity) {
        Player player = (Player) bladeEntity.getOwner();
        Level level = player.level();
        if (!level.isClientSide) {
            // 检测周围生物并造成伤害
            double range = 9.0; // 检测范围，单位是方块
            List<LivingEntity> nearbyEntities = level.getEntitiesOfClass(LivingEntity.class,
                    new AABB(bladeEntity.getX() - range, bladeEntity.getY() - range, bladeEntity.getZ() - range,
                            bladeEntity.getX() + range, bladeEntity.getY() + range, bladeEntity.getZ() + range));
            for (LivingEntity entity : nearbyEntities) {
                // 对周围生物造成伤害
                if (entity != player) {
                    entity.hurt(level.damageSources().magic(), 500f);
                }
            }
        }
    }

    public void throwBlade(Player player) {
        Level level = player.level();
        if (!level.isClientSide) {
            Resplendent_BladeEntity bladeEntity = new Resplendent_BladeEntity(ModEntities.RESPLENDENT_BLADE.get(), level,RESPLENDENT_BLADE.get());
            bladeEntity.setPos(player.getX(), player.getY(), player.getZ());
            bladeEntity.setOwner(player);
            // 设置实体的方向
            Vec3 lookVec = player.getLookAngle();
            bladeEntity.shoot(lookVec.x, lookVec.y, lookVec.z, 1.5f, 0);
            //设置实体速度
            bladeEntity.setDeltaMovement(lookVec.x * 1.5f, lookVec.y * 1.5f, lookVec.z * 1.5f);
            level.addFreshEntity(bladeEntity);
        }
    }
}

