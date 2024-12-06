// FILEPATH: E:/SBCraft-mod/SBmod/src/main/java/com/Twilight/ModEntities/client/Explosion_SheepWoolLayer.java

package com.Twilight.ModEntities.client;

import com.Twilight.ModEntities.custom.Explosion_Sheep;
import com.Twilight.SBMod.Main;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class Explosion_Sheep_WoolLayer extends RenderLayer<Explosion_Sheep, Explosion_SheepModel<Explosion_Sheep>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/entities/sheep_wool.png");
    private final Explosion_Sheep_WoolModel woolModel;

    public Explosion_Sheep_WoolLayer(RenderLayerParent<Explosion_Sheep, Explosion_SheepModel<Explosion_Sheep>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.woolModel = new Explosion_Sheep_WoolModel(modelSet.bakeLayer(ModModelLayers.EXPLOSION_SHEEP_WOOL_LAYER));
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, Explosion_Sheep sheep,
                       float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!sheep.isInvisible()) {
            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.woolModel, TEXTURE, matrixStack, buffer, packedLight, sheep,
                    limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, 1, 1, 1);
        }
    }
}
