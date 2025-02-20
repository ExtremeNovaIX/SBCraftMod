package com.Twilight.ModEntities.client.Entityrenderer;

import com.Twilight.ModEntities.client.EntityLayers.ModModelLayers;
import com.Twilight.ModEntities.client.EntityModel.Twilight_BuilderModel;
import com.Twilight.ModEntities.custom.Twilight_Builder;
import com.Twilight.SBMod.Main;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class Twilight_BuilderRenderer extends MobRenderer<Twilight_Builder, Twilight_BuilderModel<Twilight_Builder>> {
    public Twilight_BuilderRenderer(EntityRendererProvider.Context context) {
        super(context, new Twilight_BuilderModel<>(context.bakeLayer(ModModelLayers.TWILIGHT_BUILDER_LAYER)), 0.5f);
    }
    @Override
    public ResourceLocation getTextureLocation(Twilight_Builder p_114482_) {
        return new ResourceLocation(Main.MOD_ID, "textures/entities/twilight_builder.png");

    }

    @Override
    public void render(Twilight_Builder entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

}