package com.Twilight.Event;

import com.Twilight.ModItems.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

import static com.Twilight.ModEntities.client.Entityrenderer.Resplendent_BladeRenderer.ZERO_DIMENSIONS;
import static com.Twilight.ModEntities.client.Entityrenderer.Resplendent_BladeRenderer.originalDimensions;
import static com.Twilight.ModItems.Resplendent_Blade.*;

@Mod.EventBusSubscriber
public class PlayerTickHandler {
    private static final Map<Player, AABB> originalBoundingBoxes = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (isDashingingTime) {
            event.player.setNoGravity(true);
            breakBlocks(event.player, event.player.level());
            DashingTimer++;
            hurtEnemyInBlade(event.player);
            if (DashingTimer >= DashingTime) { // 冲刺结束
                isDashingingTime = false;
                DashingTimer = 0;
                event.player.setNoGravity(false);
                event.player.fallDistance = 0;
                // 恢复碰撞检测
                enableCollision(player);
            }
            disableCollision(player);
        }
    }

    private static void disableCollision(Player player) {
        // 保存原始碰撞箱
        originalBoundingBoxes.put(player, player.getBoundingBox());
        player.setBoundingBox(new AABB(0,0,0,0,0,0));
    }

    private static void enableCollision(Player player) {
        // 恢复玩家的原始碰撞箱
        AABB originalBB = originalBoundingBoxes.remove(player);
        if (originalBB != null) {
            player.setBoundingBox(originalBB);
        }
    }
    private static void resetPlayerState(Player player) {
        // 恢复状态
        isDashingingTime = false;
        DashingTimer = 0;

        // 恢复重力
        player.setNoGravity(false);
        player.fallDistance = 0;

        // 恢复碰撞箱
        EntityDimensions original = player.getDimensions(player.getPose());
        player.setBoundingBox(original.makeBoundingBox(player.position()));

        // 恢复能力
        player.getAbilities().flying = false;
        player.getAbilities().invulnerable = false;
        player.onUpdateAbilities();
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


