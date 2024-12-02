package com.Twilight.Event; // 确保导入了正确的类
import com.Twilight.ModItems.ModItems;
import com.Twilight.SBMod.Main;
import com.Twilight.Util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEnents {
    @Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvent {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.SHIT_KEY);
        }
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinding.SHIT_KEY.consumeClick()){
                Minecraft mc = Minecraft.getInstance();
                Player player = mc.player;

                if (player != null && mc.level != null) {
                    ItemStack diamondStack = new ItemStack(ModItems.SHIT.get());
                    ItemEntity diamondEntity = new ItemEntity(mc.level, player.getX(), player.getY(), player.getZ(), diamondStack);
                    mc.level.addFreshEntity(diamondEntity);
                }

            }
        }
    }
}
