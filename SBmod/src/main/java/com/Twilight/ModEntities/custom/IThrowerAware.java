package com.Twilight.ModEntities.custom;

import net.minecraft.world.entity.player.Player;

public interface IThrowerAware {
    void setThrower(Player player);
    Player getThrower();
}