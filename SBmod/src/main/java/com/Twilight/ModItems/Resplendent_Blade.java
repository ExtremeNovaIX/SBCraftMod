package com.Twilight.ModItems;

import com.Twilight.ModSounds.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;

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
}
