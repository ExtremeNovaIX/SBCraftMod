package com.Twilight.ModEntities.client.Entityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.Twilight.ModItems.ModItems.RESPLENDENT_BLADE;

@Mod.EventBusSubscriber(value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public class Resplendent_BladeRenderer {
    private static final int FULL_BRIGHT = LightTexture.pack(15, 15);

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        ItemStack stack = event.getItemStack();

        if (stack.getItem() == RESPLENDENT_BLADE.get()) {
            event.setCanceled(true);

            renderCustomWeapon(
                    event.getPoseStack(),
                    event.getMultiBufferSource(),
                    stack
            );
        }
    }

    private static void renderCustomWeapon(
            PoseStack poseStack,
            MultiBufferSource buffer,
            ItemStack stack
    ) {
        BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(stack, null, null, 0);
        renderGlowOverlay(poseStack, buffer, model, stack);
    }

    private static void applyHandTransform(PoseStack poseStack, ItemDisplayContext context) {
        if (context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND) {
            poseStack.translate(0.95, 0.2, 0.7);
            poseStack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(45));
        } else {
            poseStack.translate(0.7, 0.2, 0);
        }
        poseStack.scale(1.2F, 1.2F, 1.2F);
    }

    private static void renderGlowOverlay(
            PoseStack poseStack,
            MultiBufferSource buffer,
            BakedModel model,
            ItemStack stack
    ) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        poseStack.pushPose();
        applyHandTransform(poseStack, ItemDisplayContext.FIRST_PERSON_RIGHT_HAND);
        itemRenderer.render(
                stack,
                ItemDisplayContext.FIRST_PERSON_RIGHT_HAND,
                false,
                poseStack,
                buffer,
                FULL_BRIGHT,
                OverlayTexture.NO_OVERLAY,
                model
        );
        poseStack.popPose();
    }
}
