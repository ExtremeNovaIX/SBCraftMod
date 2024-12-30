package com.Twilight.ModItems;

import com.Twilight.ModSounds.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class Resplendent_Blade extends SwordItem {

    // 添加物品描述
    Component goldenName = Component.translatable("item.sbmod.resplendent_blade").withStyle(ChatFormatting.GOLD);
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.sbmod.resplendent_blade.desc").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, level, tooltip, flag);
    }

    enum Mode {
        NORMAL,
        DASHING,
        DEFENDING
    }
    private Mode mode = Mode.NORMAL; // 初始模式
    public Resplendent_Blade(Tier material, int damage, float speed, Properties properties) {
        super(material, damage, speed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity attacker, LivingEntity target) {
        Level level = attacker.level();
        if (!level.isClientSide) { // 服务端逻辑
            switch (mode) {
                case NORMAL:
                    level.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(),
                            ModSounds.RESPLENDENT_BLADE_NORMAL.get(),
                            SoundSource.PLAYERS, 1.0F, 1.0F);
                    break;
                case DASHING:
                    level.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(),
                            ModSounds.RESPLENDENT_BLADE_DASHING.get(),
                            SoundSource.PLAYERS, 1.0F, 1.0F);
                    break;
                case DEFENDING:
                    level.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(),
                            ModSounds.RESPLENDENT_BLADE_DEFENDING.get(),
                            SoundSource.PLAYERS, 1.0F, 1.0F);
                    break;
            }
        }
        return false;
    }
}
