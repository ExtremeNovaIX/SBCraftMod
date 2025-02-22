package com.Twilight.Event;

import com.Twilight.ModItems.ModItems;
import com.Twilight.ModItems.Resplendent_Blade;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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
    public static final Map<UUID, Vec3> dashDirections = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) return;

        Player player = event.player;
        UUID playerId = player.getUUID();
        DashState state = playerDashStates.get(playerId);

        if (state != null && state.isDashing) {
            state.timer++;

            if (!player.level().isClientSide) {
                Resplendent_Blade.breakBlocks(player);
                Resplendent_Blade.hurtEnemyInBlade(player);
            }

            if(state.isDashing) {
                // 每tick强制保持运动方向
                Vec3 direction = dashDirections.get(playerId);
                if (direction != null) {
                    player.setDeltaMovement(direction);
                    if (player instanceof ServerPlayer serverPlayer) {
                        serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(
                                serverPlayer.getId(),
                                direction
                        ));
                    }
                }
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
        player.setNoGravity(false);
        dashDirections.remove(player.getUUID());
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
