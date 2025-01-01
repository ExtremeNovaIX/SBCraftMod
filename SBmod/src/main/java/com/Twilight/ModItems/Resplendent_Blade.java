package com.Twilight.ModItems;

import com.Twilight.ModSounds.ModSounds;
import com.Twilight.Packet.CustomPacket;
import com.Twilight.SBMod.Main;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber
public class Resplendent_Blade extends SwordItem {

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
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    ModSounds.RESPLENDENT_BLADE_DASHING.get(),
                    SoundSource.PLAYERS, 0.5F, 1.0F);
            double maxSpeed = 5.0; // 最大速度
            int detectionRange = 5; // 检测范围，单位是方块

            Vec3 lookVec = player.getLookAngle();
            Vec3 direction = lookVec.normalize().scale(maxSpeed);

            // 对于玩家，使用网络包来设置移动
            if (player instanceof ServerPlayer) {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                Vec3 newMotion = serverPlayer.getDeltaMovement().add(direction);
                serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer.getId(), newMotion));
            }

            // 破坏玩家周围的方块
            destroyBlocksAroundPlayer(player, level, detectionRange);
        }
    }

    private static void destroyBlocksAroundPlayer(Player player, Level level, int detectionRange) {
        // 获取玩家位置的整数坐标
        int playerX = Mth.floor(player.getX());
        int playerY = Mth.floor(player.getY());
        int playerZ = Mth.floor(player.getZ());

        // 获取玩家周围的方块
        for (int xOff = -detectionRange; xOff <= detectionRange; xOff++) {
            for (int yOff = -detectionRange; yOff <= detectionRange; yOff++) {
                for (int zOff = -detectionRange; zOff <= detectionRange; zOff++) {
                    // 检查是否在检测范围内
                    if (Math.sqrt(xOff * xOff + yOff * yOff + zOff * zOff) <= detectionRange) {
                        // 创建BlockPos对象
                        BlockPos pos = new BlockPos(playerX + xOff, playerY + yOff, playerZ + zOff);
                        BlockState state = level.getBlockState(pos);
                        // 检查方块是否可破坏（这里可以根据需要添加更多的条件）
                        if (state.getDestroySpeed(level, pos) > 0) {
                            level.destroyBlock(pos, true);
                        }
                    }
                }
            }
        }
    }
}