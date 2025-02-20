package com.Twilight.ModItems;

import com.Twilight.ModSounds.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber
public class Resplendent_Blade extends SwordItem {
    public static boolean isDashingingTime = false;
    public static int DashingTimer = 0;
    public static int DashingTime = 40;//在这里设置冲刺时间(tick)


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
        if (!level.isClientSide) { // 服务端逻辑
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
            Dashing(player, level);
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.pass(stack);
    }

    public static void Dashing(Player player, Level level) {
        if (!level.isClientSide) {
            // 播放声音
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    ModSounds.RESPLENDENT_BLADE_DASHING.get(),
                    SoundSource.PLAYERS, 10F, 1.0F);

            double maxSpeed = 5.0; // 最大速度
            Vec3 lookVec = player.getLookAngle();
            Vec3 direction = lookVec.normalize().scale(maxSpeed);

            // 对于玩家，使用网络包来设置移动
            if (player instanceof ServerPlayer) {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                Vec3 newMotion = serverPlayer.getDeltaMovement().add(direction);
                serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer.getId(), newMotion));
            }
            isDashingingTime = true;
            DashingTimer = 0;
        }
    }

    public static void breakBlocks(Player player, Level level) {
        double range = 7.0; // 检测范围，单位是方块
        for (int x = (int) (Math.floor(player.getX()) - range); x < Math.floor(player.getX()) + range; x++) {
            for (int y = (int) (Math.floor(player.getY()) - range); y < Math.floor(player.getY()) + range; y++) {
                for (int z = (int) (Math.floor(player.getZ()) - range); z < Math.floor(player.getZ()) + range; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    // 计算当前位置与玩家位置之间的距离，如果小于等于范围，则破坏方块
                    if (Math.sqrt(Math.pow(x - player.getX(), 2) + Math.pow(y - player.getY(), 2) + Math.pow(z - player.getZ(), 2)) <= range) {
                        BlockState state = level.getBlockState(pos);
                        if (!state.isAir()) { // 检查方块是否为空
                            level.removeBlock(pos, false); // 破坏方块
                        }
                    }
                }
            }
        }
    }

    public static void hurtEnemyInBlade(Player player) {
        Level level = player.level();
        if (!level.isClientSide) { // 服务端逻辑
            // 检测周围生物并造成伤害
            double range = 9.0; // 检测范围，单位是方块
            List<LivingEntity> nearbyEntities = level.getEntitiesOfClass(LivingEntity.class,
                    new AABB(player.getX() - range, player.getY() - range, player.getZ() - range,
                            player.getX() + range, player.getY() + range, player.getZ() + range));
            for (LivingEntity entity : nearbyEntities) {
                // 对周围生物造成伤害
                if (entity != player) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.hurt(level.damageSources().magic(), 40f);
                }
                
            }
        }
    }


}

