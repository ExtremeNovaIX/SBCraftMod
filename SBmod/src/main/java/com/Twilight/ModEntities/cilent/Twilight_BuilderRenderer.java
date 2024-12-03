package com.Twilight.ModEntities.cilent;

import com.Twilight.ModEntities.custom.Twilight_Builder;
import com.Twilight.SBMod.Main;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
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
        return new ResourceLocation(Main.MOD_ID, "textures/entity/twilight_builder.png");

    }

    @Override
    public void render(Twilight_Builder p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) {


        super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
    }
}