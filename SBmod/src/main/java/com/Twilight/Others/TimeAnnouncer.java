package com.Twilight.Others;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("sbmod")
public class TimeAnnouncer {
    private static final int ANNOUNCE_INTERVAL_MINUTES = 60; // 报时间隔（分钟）
    private Timer timer;

    public TimeAnnouncer() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerAboutToStart(ServerAboutToStartEvent event) {
        MinecraftServer server = event.getServer();
        timer = new Timer();
        timer.scheduleAtFixedRate(new AnnounceTask(server), 0, ANNOUNCE_INTERVAL_MINUTES * 60 * 1000);
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        if (timer!= null) {
            timer.cancel();
            timer = null;
        }
    }

    private static class AnnounceTask extends TimerTask {
        private final MinecraftServer server;

        public AnnounceTask(MinecraftServer server) {
            this.server = server;
        }

        @Override
        public void run() {
            if (server.isRunning()) {
                ServerLevel level = server.getAllLevels().iterator().next();
                int hour = Math.toIntExact(level.getDayTime());
                hour %= 24;
                server.sendSystemMessage(Component.literal("现在是北京时间 " + hour + " 点整。"));
            }
        }
    }
}

