// FILEPATH: E:/SBCraft-mod/SBmod/src/main/java/com/Twilight/ModEntities/client/Explosion_SheepRenderer.java

package com.Twilight.ModEntities.client;

import com.Twilight.ModEntities.custom.SheepOri;
import com.Twilight.SBMod.Main;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SheepRenderer extends MobRenderer<SheepOri, Explosion_SheepModel<SheepOri>> {
    public SheepRenderer(EntityRendererProvider.Context context) {
        super(context, new Explosion_SheepModel<>(context.bakeLayer(ModModelLayers.EXPLOSION_SHEEP_LAYER)), 0.5f);
        this.addLayer(new Explosion_Sheep_WoolLayer(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(SheepOri sheep) {
        return new ResourceLocation(Main.MOD_ID, "textures/entities/sheep_explosion.png");
    }
}
