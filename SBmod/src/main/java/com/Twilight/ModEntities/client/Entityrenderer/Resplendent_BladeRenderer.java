package com.Twilight.ModEntities.client.Entityrenderer;

import com.Twilight.ModEntities.client.EntityModel.Resplendent_BladeModel;
import com.Twilight.ModEntities.custom.Resplendent_BladeEntity;
import com.Twilight.SBMod.Main;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.Twilight.ModItems.ModItems.RESPLENDENT_BLADE;

@Mod.EventBusSubscriber(value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public class Resplendent_BladeRenderer extends EntityRenderer<Resplendent_BladeEntity> {
    private final ItemRenderer itemRenderer;
    private static final int FULL_BRIGHT = LightTexture.pack(15, 15);
    private static final ResourceLocation RESPLENDENT_BLADE_TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/items/resplendent_blade.png");
    private final Resplendent_BladeModel<Resplendent_BladeEntity> model;

    private static final String TAG_LIGHT_LEVEL = "LightLevel";
    private static final int MAX_LIGHT = 15;

    // 在物品NBT中存储光源等级
    public static void setLightLevel(ItemStack stack, int level) {
        stack.getOrCreateTag().putInt(TAG_LIGHT_LEVEL, Mth.clamp(level, 0, MAX_LIGHT));
    }

    // 获取当前光源等级
    public static int getLightLevel(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(TAG_LIGHT_LEVEL) : 0;
    }

    public Resplendent_BladeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.model = new Resplendent_BladeModel<>(context.bakeLayer(Resplendent_BladeModel.LAYER_LOCATION));
    }

    @Override
    public void render(Resplendent_BladeEntity entity, float yaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(entity.getYRot()));
        poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(entity.getXRot() + 270.0F));
        poseStack.scale(1.5F, 1.5F, 1.5F);
        poseStack.translate(0.0D, -3D, 0.0D);

        this.itemRenderer.renderStatic(
                new ItemStack(RESPLENDENT_BLADE.get()),
                ItemDisplayContext.GROUND,
                FULL_BRIGHT,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                entity.level(),
                entity.getId()
        );
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(Resplendent_BladeEntity p_114482_) {
        return RESPLENDENT_BLADE_TEXTURE;
    }

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
            poseStack.translate(0.7, 0.2, 0);//变换手部位置
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
        MultiBufferSource.BufferSource glowBuffer = Minecraft.getInstance().renderBuffers().bufferSource();
        for (ItemDisplayContext context : new ItemDisplayContext[]{
                ItemDisplayContext.FIRST_PERSON_RIGHT_HAND,
                ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                ItemDisplayContext.THIRD_PERSON_LEFT_HAND
        }) {
            poseStack.pushPose();
            applyHandTransform(poseStack, context);
            itemRenderer.render(
                    stack,
                    context,
                    false,
                    poseStack,
                    glowBuffer,
                    FULL_BRIGHT,
                    OverlayTexture.NO_OVERLAY,
                    model
            );
            poseStack.popPose();
        }
        glowBuffer.endBatch(RenderType.glint());
    }
}