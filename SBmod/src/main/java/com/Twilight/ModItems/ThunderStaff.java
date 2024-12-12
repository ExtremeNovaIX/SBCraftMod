package com.Twilight.ModItems;

import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TargetBlock;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3d;
import org.joml.Vector3f;



import static net.minecraft.world.entity.EntityType.LIGHTNING_BOLT;

public class ThunderStaff extends Item {
    public ThunderStaff(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {


        Vec3 lookVec = player.getLookAngle();

        int distance = 20;
        double x = player.getX() + lookVec.x * distance;
        double y = player.getY() + lookVec.y * distance + player.getEyeHeight();
        double z = player.getZ() + lookVec.z * distance;

        LightningBolt lightningBolt = new LightningBolt(LIGHTNING_BOLT, level);
        lightningBolt.moveTo(x, y, z);

        level.addFreshEntity(lightningBolt);


        return super.use(level, player, hand);
    }
}
