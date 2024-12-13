// FILEPATH: E:/SBCraft-mod/SBmod/src/main/java/com/Twilight/ModEntities/client/Explosion_Sheep_WoolLayer.java

package com.Twilight.ModEntities.client;

import com.Twilight.ModEntities.custom.Explosion_SheepOri;
import com.Twilight.ModEntities.custom.Explosion_Sheep_Black;
import com.Twilight.ModEntities.custom.Explosion_Sheep_Red;
import com.Twilight.SBMod.Main;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class Explosion_Sheep_WoolLayer extends RenderLayer<Explosion_SheepOri, Explosion_SheepModel<Explosion_SheepOri>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/entities/sheep_wool.png");
    private final Explosion_Sheep_WoolModel woolModel;

    public Explosion_Sheep_WoolLayer(RenderLayerParent<Explosion_SheepOri, Explosion_SheepModel<Explosion_SheepOri>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.woolModel = new Explosion_Sheep_WoolModel(modelSet.bakeLayer(ModModelLayers.EXPLOSION_SHEEP_WOOL_LAYER));
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, Explosion_SheepOri sheep,
                       float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!sheep.isInvisible()) {
            float[] colors = getWoolColor(sheep);
            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.woolModel, TEXTURE, matrixStack, buffer, packedLight, sheep,
                    limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, colors[0], colors[1], colors[2]);
        }
    }

    private float[] getWoolColor(Explosion_SheepOri sheep) {
        // 根据不同的羊类型返回不同的颜色
        if (sheep instanceof Explosion_Sheep_Red) {
            return new float[]{1.0F, 0.0F, 0.0F}; // 红色
        } else if (sheep instanceof Explosion_Sheep_Black) {
            return new float[]{0.0F, 0.0F, 0.0F};
        }
        return new float[]{1.0F, 1.0F, 1.0F};
    }
}