package com.Twilight.Event;

import com.Twilight.ModItems.ModItems;
import com.Twilight.ModItems.Resplendent_Blade;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.Twilight.ModItems.Resplendent_Blade.*;

@Mod.EventBusSubscriber
public class PlayerTickHandler {
    private static final Map<UUID, DashState> playerDashStates = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) return;

        Player player = event.player;
        UUID playerId = player.getUUID();
        DashState state = playerDashStates.get(playerId);

        // 处理现有冲刺状态
        if (state != null && state.isDashing) {
            state.timer++;

            // 执行逻辑
            if (!player.level().isClientSide) {
                Resplendent_Blade.breakBlocks(player);
                Resplendent_Blade.hurtEnemyInBlade(player);
            }

            // 结束检测
            if (state.timer >= Resplendent_Blade.DashingTime + 10) {
                state.isDashing = false;
                state.timer = 0;
                player.setNoGravity(false);
            }
        }
    }

    public static void startDash(Player player) {
        DashState state = new DashState();
        state.isDashing = true;
        state.timer = 0;
        playerDashStates.put(player.getUUID(), state);
    }

    private static class DashState {
        boolean isDashing = false;
        int timer = 0;
    }

    private static void resetPlayerState(Player player) {
        isDashingingTime = false;
        DashingTimer = 0;

        player.setNoGravity(false);
        player.fallDistance = 0;
        player.noPhysics = false;
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        // 登录时强制恢复状态
        resetPlayerState(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        // 登出时强制恢复状态
        resetPlayerState(event.getEntity());
    }
}
